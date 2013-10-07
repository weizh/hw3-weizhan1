package edu.cmu.deiis.annotators;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.TypeNameMap;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * Generating annotations for tokens and sentences. Token: token, lemma, pos, NER tags and dependencies for sentence.
 * 
 * @author Wei Zhang
 * 
 */
public class WordFeatureAnnotator extends CasAnnotator_ImplBase implements TypeNameMap {
  
  // The uima types
  private static Type Question, Answer, Token;

  // The taggers

  private static StanfordCoreNLP pipeline;

  private static AbstractSequenceClassifier<CoreLabel> nerTagger;
 
  // syntactic parser.
  private static LexicalizedParser lp;

  private static TreebankLanguagePack tlp;

  private static GrammaticalStructureFactory gsf;

  /**
   * initialize the resources for taggers.
   */
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    String NERPath = "src/main/resources/stanfordModels/english.all.3class.distsim.crf.ser.gz";
    
    // ner initialization
    nerTagger = CRFClassifier.getClassifierNoExceptions(NERPath);

    // stem, pos tagging initialization
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
    props.put("pos.model", "src/main/resources/stanfordModels/english-left3words-distsim.tagger");
    props.put("parse.model", "src/main/resources/stanfordModels/englishPCFG.ser.gz");
    pipeline = new StanfordCoreNLP(props);

    String grammar = "src/main/resources/stanfordModels/englishPCFG.ser.gz";
    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
    lp = LexicalizedParser.loadModel(grammar, options);
    tlp = lp.getOp().langpack();
    gsf = tlp.grammaticalStructureFactory();

  }

  // import the types from the typeSystem environment
  @Override
  public void typeSystemInit(TypeSystem aTypeSystem) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    super.typeSystemInit(aTypeSystem);
    Question = aTypeSystem.getType(QUESTION_TYPE_NAME);
    Answer = aTypeSystem.getType(ANSWER_TYPE_NAME);
    Token = aTypeSystem.getType(TOKEN_TYPE_NAME);
  }

  /**
   * Generate lemma, pos, ner tags for tokens,
   * Generate the dependencies for sentence.
   * Note: Abandoned NER tags. set them to null.
   */
  @Override
  public void process(CAS aCAS) throws AnalysisEngineProcessException {

    FSIterator<AnnotationFS> fs = aCAS.getAnnotationIndex().iterator();

    // build a temp token array to hold the uima tokens. no matter the sentence. Only tokens
    ArrayList<edu.cmu.deiis.types.Token> uimaTokenBuff = new ArrayList<edu.cmu.deiis.types.Token>();

    while (fs.hasNext()) {
      // iterate each annotation in the context.
      AnnotationFS f = fs.next();

      // if it is a question or an answer, then parse it, and generate tokens.
      if (f.getType().getName().equals(Question.getName())
              || f.getType().getName().equals(Answer.getName())) {
        // read the text for question or answer.
        String text = f.getCoveredText();
        int uimaSentOffset = f.getBegin();
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text. The Text is most probably not a Document, but a
        // sentence.
        pipeline.annotate(document);

        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom
        // types
        // these are all the sentences in this document
        List<CoreMap> stanfordSentences = document.get(SentencesAnnotation.class);
        for (CoreMap stanfordSentence : stanfordSentences) {

          // traversing the words in the current sentence
          // a CoreLabel is a CoreMap with additional token-specific methods
          for (CoreLabel stanfordToken : stanfordSentence.get(TokensAnnotation.class)) {

            // construct a uima token, for holding the features.
            edu.cmu.deiis.types.Token uimaToken = (edu.cmu.deiis.types.Token) aCAS
                    .createAnnotation(Token, uimaSentOffset + stanfordToken.beginPosition(),
                            uimaSentOffset + stanfordToken.endPosition());

            // this is the text of the token
            uimaToken.setToken(stanfordToken.get(TextAnnotation.class));
            // add the features into the uima token.
            uimaToken.setMorph(stanfordToken.get(CoreAnnotations.LemmaAnnotation.class));
            // this is the POS tag of the token
            uimaToken.setPos(stanfordToken.get(PartOfSpeechAnnotation.class));

            // add the temporary token into the token buffer.
            uimaTokenBuff.add(uimaToken);
          }
        }

        // add ner code here
        ArrayList<String> uimaNERTagBuff = new ArrayList<String>();
        List<List<CoreLabel>> out = nerTagger.classify(text);
        for (List<CoreLabel> sentence : out) {
          for (CoreLabel word : sentence) {
            uimaNERTagBuff.add(word.get(CoreAnnotations.AnswerAnnotation.class));
          }
        }

        //Deprecated. Alignment failed!
        
        // align NER tag with tokens. put the NER tags to each token.
//        for (int i = 0; i < uimaTokenBuff.size(); i++) {
//          uimaTokenBuff.get(i).setNer(uimaNERTagBuff.get(i));
//        }

        // add parser code here.
        Iterable<List<? extends HasWord>> sentences;

        // tokenize the "document", to get the sentences.
        Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(
                new StringReader(text));
        List<? extends HasWord> sentence = toke.tokenize();
        Tree parse = lp.parse(sentence);
        // parse.pennPrint();
        // System.out.println();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
        ArrayList<String> dependencies = new ArrayList<String>();
        for (TypedDependency td : tdl) {
          dependencies.add(td.toString());
        }
        try {
          StringArray a = new StringArray(aCAS.getJCas(), tdl.size());
          a.copyFromArray(dependencies.toArray(new String[] {}), 0, 0, tdl.size());
          if (f.getType().getName().equals(Question.getName()))
            ((edu.cmu.deiis.types.Question) f).setDependencies(a);

          if (f.getType().getName().equals(Answer.getName()))
            ((edu.cmu.deiis.types.Answer) f).setDependencies(a);

        } catch (CASException e) {
          e.printStackTrace();
        }
      }// end if A Question or A Answer
    }// end of Question and Answer sentence loop
    
    // add the data structures to the CasIndex.
    for (edu.cmu.deiis.types.Token token : uimaTokenBuff){
      token.setCasProcessorId("edu.cmu.deiis.WordFeatureAnnotator");
      token.setConfidence(1);
      aCAS.addFsToIndexes(token);
    }
  }
}
