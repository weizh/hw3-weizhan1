

/* First created by JCasGen Sun Sep 22 22:11:00 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;


/** 
 * Updated by JCasGen Sun Oct 06 21:09:52 EDT 2013
 * XML source: /Users/indri/git/hw2/hw2-weizhan1/src/main/resources/hw2-weizhan1-aae.xml
 * @generated */
public class Question extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Question(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: dependencies

  /** getter for dependencies - gets The dependency pairs for the sentence of the question
   * @generated */
  public StringArray getDependencies() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Question");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies)));}
    
  /** setter for dependencies - sets The dependency pairs for the sentence of the question 
   * @generated */
  public void setDependencies(StringArray v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dependencies - gets an indexed value - The dependency pairs for the sentence of the question
   * @generated */
  public String getDependencies(int i) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Question");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies), i);}

  /** indexed setter for dependencies - sets an indexed value - The dependency pairs for the sentence of the question
   * @generated */
  public void setDependencies(int i, String v) { 
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Question");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_dependencies), i, v);}
  }

    