package edu.cmu.deiis.annotators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

/**
 * File Input Annotator, for converting the input file string bulk into question and answer
 * structures.
 * 
 * Process result : Question, Answer.
 * @author Wei Zhang
 * 
 */
public class FileInput extends JCasAnnotator_ImplBase {

 
  private String text;

  /**
   * Main process for constructing the Question and Answers.
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    
    // get text
    text = aJCas.getDocumentText().trim();
    String[] lines = text.split("\n");
    int offset = 0;
    // loop each line
    for (String line : lines) {
      if (line.trim().startsWith("Q")) {
        //create a Question if starts with Q
        Question q = new Question(aJCas, offset + 2, offset + line.length()-1);
        q.setCasProcessorId("edu.cmu.deiis.annotators.FileInput");
        q.setConfidence(1);
        q.addToIndexes();
        offset += line.length() + 1;
      }
      if (line.trim().startsWith("A")) {
        //create an Answer if starts with A
        Answer a = new Answer(aJCas, offset + 4, offset + line.length()-1);
        a.setIsCorrect(false);
        if (line.charAt(2) == '1')
          a.setIsCorrect(true);
        a.setCasProcessorId("edu.cmu.deiis.annotators.FileInput");
        a.setConfidence(1);
        a.addToIndexes();
        offset += line.length() + 1;
      }
    }//for each line
    
  }// process
  
}// class
