package edu.cmu.deiis;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;

public class hw2CasConsumer extends CasConsumer_ImplBase implements TypeNameMap {

  private static Type Question, Answer, Token, NGram, Annotation, AnswerScore;

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {

    Question question = (Question) aCAS.getAnnotationIndex(Question).iterator().next();
    
     FSIterator<AnnotationFS> answerScoreIter = ( aCAS
            .getAnnotationIndex(AnswerScore)).iterator();

     ArrayList<AnswerScore> answerScores = new ArrayList<AnswerScore>();

     while (answerScoreIter.hasNext()) {
       answerScores.add((edu.cmu.deiis.types.AnswerScore) answerScoreIter.next());
     }
     
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

  }

  @Override
  public void typeSystemInit(TypeSystem aTypeSystem) throws ResourceInitializationException {
    // TODO Auto-generated method stub
    super.typeSystemInit(aTypeSystem);
    super.typeSystemInit(aTypeSystem);
    Question = aTypeSystem.getType(QUESTION_TYPE_NAME);
    Answer = aTypeSystem.getType(ANSWER_TYPE_NAME);
    Token = aTypeSystem.getType(TOKEN_TYPE_NAME);
    NGram = aTypeSystem.getType(NGRAM_TYPE_NAME);
    Annotation = aTypeSystem.getType(ANNOTATION_TYPE_NAME);
    AnswerScore = aTypeSystem.getType(ANSWERSCORE_TYPE_NAME);

  }

}
