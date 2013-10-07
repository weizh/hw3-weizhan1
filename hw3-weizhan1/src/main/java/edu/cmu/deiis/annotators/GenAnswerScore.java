package edu.cmu.deiis.annotators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.AnnotationUtils;
import edu.cmu.deiis.TypeNameMap;
import edu.cmu.deiis.types.*;

/**
 * Main Function for Score Generation and Evaluation.
 * 
 * Process: 
 * Generate Question features
 * 
 * for each answer:
 *    Generate Answer Features
 *    generate QA matching score
 *    generatin AnswerScore structure
 * 
 * put AnswerScore in the CAS index
 * 
 * Evaluation, output in console the P@N.
 * 
 * @author Wei Zhang
 *
 */
public class GenAnswerScore extends CasAnnotator_ImplBase implements TypeNameMap {

  // the types that are got from the inital system method.
  private static Type Question, Answer, Token, NGram, Annotation, AnswerScore;


  @SuppressWarnings("unchecked")
  @Override
  public void process(CAS aCAS) throws AnalysisEngineProcessException {
    // Temp list to store the generated answer scores.
    ArrayList<AnswerScore> answerScores = new ArrayList<AnswerScore>();

    /**
     * Generate the Question features
     */
    
    Question question = (Question) aCAS.getAnnotationIndex(Question).iterator().next();

    // ngram feature
    ArrayList<NGram> questionNGram = getNGramInRange(aCAS, question.getBegin(), question.getEnd());
    ArrayList<NGram> qUniGram = getNGramInGramGroup(questionNGram, 1);
    ArrayList<NGram> qBiGram = getNGramInGramGroup(questionNGram, 2);
    ArrayList<NGram> qTriGram = getNGramInGramGroup(questionNGram, 3);

    // bag of nouns featue
    ArrayList<edu.cmu.deiis.types.Token> qNounTokens = getTokensByType(aCAS, question.getBegin(),
            question.getEnd(),"Noun");

    // parsing feature
    ArrayList<Token> qTokens = AnnotationUtils.getTokensInSentence(question, aCAS);

    ArrayList<String> qDep = new ArrayList<String>();
    StringArray qDependencies = question.getDependencies();
    for (int i = 0; i < qDependencies.size(); i++) {
      String dep = qDependencies.get(i);
      // the triple returned is the normalized tuple : agent(killed-3, booth) -> dobj-kill-booth.
      String triple = parseRelation(dep, qTokens);
      // filtering the relations, select out nsubj, dobj, and neg. nsubj-kill-booth or neg-kill.
      qDep.add(triple);
    }

    /**
     * loop each answer
     */
    FSIterator<AnnotationFS> answerIter = aCAS.getAnnotationIndex(Answer).iterator();
    while (answerIter.hasNext()) {
      /**
       * An Answer
       */
      Answer answer = (Answer) answerIter.next();

      // ngram feature
      ArrayList<NGram> answerNGram = getNGramInRange(aCAS, answer.getBegin(), answer.getEnd());
      ArrayList<NGram> aUniGram = getNGramInGramGroup(answerNGram, 1);
      ArrayList<NGram> aBiGram = getNGramInGramGroup(answerNGram, 2);
      ArrayList<NGram> aTriGram = getNGramInGramGroup(answerNGram, 3);

      // bag of nouns feature
      ArrayList<edu.cmu.deiis.types.Token> aNounTokens = getTokensByType(aCAS, answer.getBegin(),
              answer.getEnd(),"Noun");

      // parsing feature
      ArrayList<Token> aTokens = AnnotationUtils.getTokensInSentence(answer, aCAS);

      ArrayList<String> aDep = new ArrayList<String>();

      StringArray aDependencies = answer.getDependencies();
      System.out.println(aDependencies.size());
      for (int i = 0; i < aDependencies.size(); i++) {
        String dep = aDependencies.get(i);
        // the triple returned is the normalized tuple : agent(killed-3, booth) -> dobj-kill-booth.
        String triple = parseRelation(dep, aTokens);
        // filtering the relations, select out nsubj, dobj, and neg. nsubj-kill-booth or neg-kill.
        aDep.add(triple);
      }
      /**
       * Generate score for this answer based on feature matching scores.
       */

      // Generate ngram match scores
      double uniGramSim = getSpanArrayListSimilarity(qUniGram, aUniGram);
      double biGramSim = getSpanArrayListSimilarity(qBiGram, aBiGram);
      double triGramSim = getSpanArrayListSimilarity(qTriGram, aTriGram);
      double nGramScore = uniGramSim / 2.0d + biGramSim / 3.0d + triGramSim / 6.0d;

      // Generate bag of noun(bon) match scores
      double bonScores = getBagOfNounSimilarity(qNounTokens, aNounTokens);

      // Generate parsing match scores
      double parseScore = getParseSimilarity(aDep, qDep);

      
      // combine those scores to generate the final score. tune the parameters inside this function.
      double score = combineScores(nGramScore, bonScores, parseScore);

      // create AnswerScore object, and add it to the answerScores buffer Array.
      edu.cmu.deiis.types.AnswerScore answerScore = (AnswerScore) aCAS.createAnnotation(
              AnswerScore, answer.getBegin(), answer.getEnd());
      answerScore.setAnswer(answer);
      answerScore.setScore(score);
      answerScore.setCasProcessorId("edu.cmu.deiis.annotators.GenAnswerScore");
      answerScore.setConfidence(1);
      answerScores.add(answerScore);

    }
    /**
     *  add answer score to indexes.
     */
    for (AnswerScore answerScore : answerScores) {
      aCAS.addFsToIndexes(answerScore);
    }
    
    /**
     * evaluation: Rank answerScores by confidence value, and generate P@N.
     */
    //get N:
    int N=0;
    for (AnswerScore answerScore : answerScores) 
      if (answerScore.getAnswer().getIsCorrect()==true)
        N++;
    
    //sort collection in descending order.
    Collections.sort(answerScores);
    
    // output the result.
    int i=0;
    double agree = 0;
    System.out.println("\nThe Evaluation begins for question : " + question.getCoveredText());
    for(AnswerScore answerScore:answerScores){
      if(i++>=N){System.out.println("P@N is " +agree/(double)N);break;}
      if(answerScore.getAnswer().getIsCorrect()==true)agree+=1;
      System.out.println("Answer number "+ i +" : " +answerScore.getAnswer().getCoveredText());
      System.out.println("Generated score: "+answerScore.getScore());
      System.out.println("Gold label :" +answerScore.getAnswer().getIsCorrect());
    }
    
    
  }// end of process

  /**
   * Combine the scores of ngram, bonScore, and parseScore
   * The weights are heuristically assigned.
   * @param nGramScore
   * @param bonScores
   * @param parseScore
   * @return
   */
  private double combineScores(double nGramScore, double bonScore, double parseScore) {
    
    return (0.4*nGramScore+0.1*bonScore+0.5*parseScore);
  }

  /**
   * Generate the parse similarities
   * Features:
   *     agreement 1: obj+subj+polarity
   *     agreement 2: verb+polarity
   *     agreement 3: obj + verb + polarity
   *     aggrement 4: subj +verb + polarity
   *     
   *     Note: The verbs are converted to the same lemma in the aggrements.
   * @param aDep
   * @param qDep
   * @return
   */
  private double getParseSimilarity(ArrayList<String> aDep, ArrayList<String> qDep) {

    String aVerb = null, qVerb = null, aobj = null, qobj = null, asubj = null, qsubj = null;
    boolean aNeg = false, qNeg = false; // false to repesent not have. true for have.
    for (String a : aDep) {
      String[] toks = a.split("-");
      if (a.startsWith("nsubj")) {
        aVerb = toks[1];
        asubj = toks[2];
      }
      if (a.startsWith("nobj")) {
        aVerb = toks[1];
        aobj = toks[2];
      }
      if (a.startsWith("neg"))
        aNeg = true;
    }
    for (String q : qDep) {
      String[] toks = q.split("-");
      if (q.startsWith("nsubj")) {
        qVerb = toks[1];
        qsubj = toks[2];
      }
      if (q.startsWith("nobj")) {
        qVerb = toks[1];
        qobj = toks[2];
      }
      if (q.startsWith("neg"))
        qNeg = true;
    }

    boolean verbSim = similarInWordNet(aVerb, POS.VERB, qVerb, POS.VERB);
    if (verbSim == true)
      qVerb = aVerb; // cast qVerb to aVerb

    boolean[] agg = new boolean[4];
    // agreement 1: obj+subj+polarity
    if (aobj != null && qobj != null && aobj.equals(qobj)) {
      if (asubj != null && qsubj != null && asubj.equals(qsubj)) {
          agg[0] = aNeg==qNeg;
      }
    }
    // agreement 2: verb+polarity
    if (aVerb!=null && qVerb!=null& aVerb.equals(qVerb))
        agg[1]= aNeg==qNeg;
    // agreement 3: obj + verb + polarity
    if (aobj!=null && qobj!=null && aobj.equals(qobj)){
      if (aVerb.equals(qVerb))
        agg[2] = aNeg==qNeg;
    }
    // agreement 4: subj +verb + polarity
    if (asubj!=null && qsubj!=null && asubj.equals(qsubj)){
      if (aVerb.equals(qVerb))
        agg[3] = aNeg==qNeg;
    }
    
    double positives =0.0d;
    for (boolean b : agg){
      if (b==true)positives+=1;
    }
    return positives/(double)agg.length;
  }
  
/**
 * Check if the two verbs is in the same synset in wordnet.
 * @param aVerb
 * @param verb
 * @param qVerb
 * @param verb2
 * @return
 */
  private boolean similarInWordNet(String aVerb, POS aPos, String bVerb, POS bPOS) {
    String[] aOffsets, qOffsets;
    try {
      aOffsets = getOffsets(aVerb, aPos);
      qOffsets = getOffsets(bVerb, bPOS);
    } catch (JWNLException e) {
      // TODO Auto-generated catch block
      System.err.println("Wornet Offset Set generation error. Return false as default.");
      return false;
    }
    if (aOffsets == null)
      return false;
    if (qOffsets == null)
      return false;
    HashSet<String> qOffsetSet = new HashSet<String>(Arrays.asList(qOffsets));
    for (String offset : aOffsets)
      if (qOffsetSet.contains(offset))
        return true;
    return false;
  }

  /**
   * Parse the dependencies. agent->nsubj, nsubjpass ->dobj. 
   * agent(kill-3,police-1) -> nsubj-kill-police
   * 
   * @param dep
   * @param qTokens
   * @param
   * @return
   */
  private String parseRelation(String dep, ArrayList<edu.cmu.deiis.types.Token> qTokens) {
//    System.out.println(dep);
    // remove paranthetis at the end.
    dep = dep.substring(0,dep.length()-1);
    String relation = dep.split("[(]")[0].trim();
    // originally first is the string. Finally it's mapped to the wordnet synonym id.
    // if relation is neg, first is the noun, then leave it alone.
    String first = dep.split("[(]")[1].trim().split("[,]")[0].trim();
    // originally second is a string. Finally it's turned into lemma, although it's a noun.
    String second = dep.split("[(]")[1].trim().split("[,]")[1].trim();

    if (relation.equals("neg")) {
      return "neg" + "-" + first.split("-")[0].trim();
    }

    if (relation.equals("agent")) {
      relation = "nsubj";
    } else if (relation.equals("nsubjpass")) {
      relation = "dobj";
    }
    // convert verb to synonym.
    String temp = (getTokenLemma(first.split("[-]")[0].trim(),
            Integer.parseInt(first.split("[-]")[1].trim()), qTokens));
    if (temp == null)
      first = first.split("[-]")[0].trim().toLowerCase();
    else
      first = temp;
    // convert object or subject to lemma.
    temp = getTokenLemma(second.split("[-]")[0].trim(),
            Integer.parseInt(second.split("[-]")[1].trim()), qTokens);
    if(temp==null)
      second = second.split("[-]")[0].trim().toLowerCase();
    else
      second = temp;
    return relation + "-" + first + "-" + second;
  }

  /**
   * get the synonyms for tokenLemma given the pos, as Offset(wordnet id) array.
   * @param tokenLemma
   * @param pos
   * @return
   * @throws JWNLException
   */
  private String[] getOffsets(String tokenLemma, POS pos) throws JWNLException {
    // TODO Auto-generated method stub
    if (tokenLemma == null)
      return null;

    ArrayList<String> offsets = new ArrayList<String>();
    offsets.add(tokenLemma);
    IndexWord indexWord = Dictionary.getInstance().getIndexWord(pos, tokenLemma);
    Synset[] set = indexWord.getSenses();
    // for every sense for tokenLemma
    for (Synset syn : set) {
      Pointer[] pointerArr = syn.getPointers(PointerType.HYPERNYM);
      // for every hypernym for a sense
      for (Pointer x : pointerArr) {
        Synset target = x.getTargetSynset();
        // for each string in hypernym
        for (Word a : target.getWords())
          offsets.add(a.getLemma());
      }
    }
    return offsets.toArray(new String[] {});
  }

  /**
   * get the lemma of the token, given the position in the sentence.
   * Note: the position may not be accurate, so abandon that for now. Just match the token string itself.
   * @param key
   * @param position
   * @param qTokens
   * @return
   */
  private String getTokenLemma(String key, int position,
          ArrayList<edu.cmu.deiis.types.Token> qTokens) {

    for (int i = 0; i < qTokens.size(); i++) {
      if (key.equals(qTokens.get(i).getCoveredText()))
        return qTokens.get(i).getMorph();
    }
    return null;
  }

  /**
   * Generate the bag of noun similarity as feature value.
   * @param qNounTokens
   * @param aNounTokens
   * @return
   */
  private double getBagOfNounSimilarity(ArrayList<edu.cmu.deiis.types.Token> qNounTokens,
          ArrayList<edu.cmu.deiis.types.Token> aNounTokens) {

    // create the lemma bags.
    HashMap<String, Double> qLemmas = new HashMap<String, Double>();
    HashMap<String, Double> aLemmas = new HashMap<String, Double>();
    for (edu.cmu.deiis.types.Token token : qNounTokens)
      putAnnotationStringInHashMap(token.getMorph(), qLemmas);
    for (edu.cmu.deiis.types.Token token : aNounTokens)
      putAnnotationStringInHashMap(token.getMorph(), aLemmas);

    return 0;
  }

  /**
   * Generate the noun tokens in the given span.
   * @param aCAS
   * @param begin
   * @param end
   * @return
   */
  private ArrayList<edu.cmu.deiis.types.Token> getTokensByType(CAS aCAS, int begin, int end, String posStrType) {
    // TODO Auto-generated method stub
    ArrayList<edu.cmu.deiis.types.Token> nounLemmas = new ArrayList<edu.cmu.deiis.types.Token>();
    FSIterator<AnnotationFS> tokensIter = aCAS.getAnnotationIndex(Token).iterator();
    while (tokensIter.hasNext()) {
      edu.cmu.deiis.types.Token token = (edu.cmu.deiis.types.Token) tokensIter.next();
      if (token.getBegin() >= begin && token.getEnd() <= end) {
        if (token.getPos().startsWith("N"))
          nounLemmas.add(token);
      }
    }
    return nounLemmas;
  }

  /**
   * Generate the similarity of the ArraySpan, which is the similarity of two arraylist of annotations.
   * @param a
   * @param b
   * @return
   */
  private double getSpanArrayListSimilarity(ArrayList<edu.cmu.deiis.types.NGram> a,
          ArrayList<edu.cmu.deiis.types.NGram> b) {
    HashMap<String, Double> ha = new HashMap<String, Double>();
    HashMap<String, Double> hb = new HashMap<String, Double>();
    for (NGram nGa : a)
      putAnnotationSpanInHashMap(nGa, ha);
    for (NGram nGb : b)
      putAnnotationSpanInHashMap(nGb, hb);
    return CosineSimilarity4HashMap(ha, hb);
  }

  /**
   * Class for generating the cosine similarity for two hashmaps.
   * Key is the string, value is the weight of the string.
   * each key in the hash map is weighted by the value. So the length of the total is the addition
   * 
   * @param ha
   * @param hb
   * @return
   */

  private double CosineSimilarity4HashMap(HashMap<String, Double> ha, HashMap<String, Double> hb) {
    double aLen = 0;
    for (Entry a : ha.entrySet()) {
      aLen += Math.pow((Double) a.getValue(), 2);
    }
    aLen = Math.sqrt(aLen);
    double bLen = 0;
    for (Entry b : hb.entrySet()) {
      bLen += Math.pow((Double) b.getValue(), 2);
    }
    bLen = Math.sqrt(bLen);
    double overlap = 0;
    for (Entry e : ha.entrySet()) {
      if (hb.containsKey(e.getKey())) {
        overlap += ha.get(e.getKey()) * hb.get(e.getKey());
      }
    }
    return overlap / (aLen * bLen);
  }

  /**
   * Helper for putting a String into a hashMap.
   * @param ta
   * @param ha
   */
  private void putAnnotationStringInHashMap(String ta, HashMap<String, Double> ha) {
    if (ha.containsKey(ta))
      ha.put(ta, ha.get(ta) + 1.0d);
    else
      ha.put(ta, 1.0d);
  }
/**
 * Helper for putting the annotation span into the hashmap.
 * @param anno
 * @param ha
 */
  private void putAnnotationSpanInHashMap(Annotation anno, HashMap<String, Double> ha) {
    String ta = anno.getCoveredText().trim().toLowerCase();
    if (ha.containsKey(ta))
      ha.put(ta, ha.get(ta) + 1.0d);
    else
      ha.put(ta, 1.0d);
  }
/**
 * Generating the ngrams given the specific n.
 * This is for distinguishing the 1,2,3 grams, to weight each of the types differently.
 * @param answerNGram
 * @param i
 * @return
 */
  private ArrayList<NGram> getNGramInGramGroup(ArrayList<NGram> answerNGram, int n) {
    ArrayList<edu.cmu.deiis.types.NGram> nGrams = new ArrayList<NGram>();
    for (NGram ngram : answerNGram) {
      if (ngram.getElementType().equals(n + "_gram"))
        nGrams.add(ngram);
    }
    return nGrams;
  }

  /**
   * get the ngrams in the range given by start and end. 
   * This is used for getting the ngrams for the sentence.
   * @param aCAS
   * @param start
   * @param end
   * @return
   */
  private ArrayList<NGram> getNGramInRange(CAS aCAS, int start, int end) {
    FSIterator<AnnotationFS> nGramIter = aCAS.getAnnotationIndex(NGram).iterator();
    ArrayList<NGram> ngrams = new ArrayList<NGram>();
    while (nGramIter.hasNext()) {
      NGram nGram = (NGram) nGramIter.next();
      if (nGram.getBegin() >= start && nGram.getEnd() <= end)
        ngrams.add(nGram);
    }
    return ngrams;
  }

  // get the types from the environment
  @Override
  public void typeSystemInit(TypeSystem aTypeSystem) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    super.typeSystemInit(aTypeSystem);
    Question = aTypeSystem.getType(QUESTION_TYPE_NAME);
    Answer = aTypeSystem.getType(ANSWER_TYPE_NAME);
    Token = aTypeSystem.getType(TOKEN_TYPE_NAME);
    NGram = aTypeSystem.getType(NGRAM_TYPE_NAME);
    Annotation = aTypeSystem.getType(ANNOTATION_TYPE_NAME);
    AnswerScore = aTypeSystem.getType(ANSWERSCORE_TYPE_NAME);
  }

  //initalize wordnet.
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    // load the wordnet tools
    try {
      JWNL.initialize(new FileInputStream("src/main/resources/WordNetDict/file_properties.xml"));
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (JWNLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

  }

}
