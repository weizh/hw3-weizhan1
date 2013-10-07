package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.Collections;

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
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.AnnotationUtils;
import edu.cmu.deiis.TypeNameMap;
import edu.cmu.deiis.types.*;

/**
 * Annotate the span as NGram, the type string is "n_gram". where n could be 1, 2, 3, ...
 * 
 * @author Wei Zhang
 * 
 */
public class NGramAnnotator extends CasAnnotator_ImplBase implements TypeNameMap {

  public static Type Question, Answer, Token, NGram, Annotation;

  // the number of grams
  int n;

  /**
   * Main process, for generating the ngram bags, and add them directly to the shared CAS.
   */
  @SuppressWarnings("unchecked")
  @Override
  public synchronized void process(CAS aCAS) throws AnalysisEngineProcessException {

    // To store the ngram as buffer.
    ArrayList<NGram> ngrams = new ArrayList<NGram>();

    // generate ngrams for Question and Answers.
    FSIterator<AnnotationFS> fsIter = aCAS.getAnnotationIndex().iterator();
    while (fsIter.hasNext()) {
      AnnotationFS fs = fsIter.next();
      String name = fs.getType().getName();
      if (name.equals(QUESTION_TYPE_NAME) || name.equals(ANSWER_TYPE_NAME)) {
        ArrayList<Token> tokensInSentence = AnnotationUtils.getTokensInSentence(fs, aCAS);
        // sort the tokens in the sentence, for generating the token sequence natrual order.
        Collections.sort(tokensInSentence);

        // note down the end of the ngram start, when looping the token array( sentence).
        int endOfLoopStart = tokensInSentence.size() - n + 1;
        // if n is more than the length of the sentence, then continue to next sentence.
        if (endOfLoopStart <= 0)
          continue;

        // temporary storing elements in a ngram
        ArrayList<Annotation> tempElements;

        // for each ngram in sentence:
        for (int i = 0; i < endOfLoopStart; i++) {
          int nGramBegin = tokensInSentence.get(i).getBegin();
          int nGramend = tokensInSentence.get(i + n - 1).getEnd();

          // store the temporary element array for a ngram.
          tempElements = new ArrayList<Annotation>();
          for (int j = i; j < i + n; j++) {
            Annotation ele = (Annotation) aCAS.createAnnotation(Annotation, tokensInSentence.get(j)
                    .getBegin(), tokensInSentence.get(j).getEnd());
            ele.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator-element");
            ele.setConfidence(1);
            tempElements.add(ele);
          }

          // generate an feature structure array, and store it to the ngram elements field.
          FSArray v = null;
          try {
            v = new FSArray(aCAS.getJCas(), n);
          } catch (CASException e) {
            System.err.println("Casting CAS to JCas failed.");
          }
          v.copyFromArray(tempElements.toArray(new Annotation[] {}), 0, 0, tempElements.size());
          NGram nGram = (NGram) aCAS.createAnnotation(NGram, nGramBegin, nGramend);
          nGram.setElements(v);
          // the type of gram is "n_gram".
          nGram.setElementType(n + "_gram");
          nGram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
          nGram.setConfidence(1);
          ngrams.add(nGram);
        }
      }
    }
    for (NGram ngram : ngrams)
      aCAS.addFsToIndexes(ngram);

  }// end process

  @Override
  public synchronized void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // get gram from the environment
    n = Integer.parseInt((String) aContext.getConfigParameterValue("n"));
  }

  @Override
  public synchronized void typeSystemInit(TypeSystem aTypeSystem)
          throws AnalysisEngineProcessException {
    // get the types from the environment.
    super.typeSystemInit(aTypeSystem);
    Question = aTypeSystem.getType(QUESTION_TYPE_NAME);
    Answer = aTypeSystem.getType(ANSWER_TYPE_NAME);
    Token = aTypeSystem.getType(TOKEN_TYPE_NAME);
    NGram = aTypeSystem.getType(NGRAM_TYPE_NAME);
    Annotation = aTypeSystem.getType(ANNOTATION_TYPE_NAME);
  }

}
