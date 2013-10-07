package edu.cmu.deiis;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;

import edu.cmu.deiis.types.Token;

public class AnnotationUtils {

  public static ArrayList<Token> getTokensInSentence(AnnotationFS fs, CAS aCAS) {
    // get all the tokens.
    ArrayList<Token> tokens = new ArrayList<Token>();
    FSIterator<AnnotationFS> tokenIter = aCAS.getAnnotationIndex().iterator();
    while (tokenIter.hasNext()) {
      AnnotationFS f = tokenIter.next();
      if(f.getType().getName().equals("edu.cmu.deiis.types.Token"))
        tokens.add(((Token)f));
    }

    ArrayList<edu.cmu.deiis.types.Token> sentenceTokens = new ArrayList<Token>();
    int start = fs.getBegin();
    int end = fs.getEnd();
    for (edu.cmu.deiis.types.Token token : tokens) {
      if (token.getBegin() >= start && token.getEnd() <= end)
        sentenceTokens.add(token);
    }
    Collections.sort(sentenceTokens);
    return sentenceTokens;
  }
}
