

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
public class Answer extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Answer.class);
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
  protected Answer() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Answer(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Answer(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Answer(JCas jcas, int begin, int end) {
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
  //* Feature: isCorrect

  /** getter for isCorrect - gets 
   * @generated */
  public boolean getIsCorrect() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.deiis.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect);}
    
  /** setter for isCorrect - sets  
   * @generated */
  public void setIsCorrect(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.deiis.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect, v);}    
   
    
  //*--------------*
  //* Feature: dependencies

  /** getter for dependencies - gets The dependency pairs for the answer.
   * @generated */
  public StringArray getDependencies() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies)));}
    
  /** setter for dependencies - sets The dependency pairs for the answer. 
   * @generated */
  public void setDependencies(StringArray v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dependencies - gets an indexed value - The dependency pairs for the answer.
   * @generated */
  public String getDependencies(int i) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies), i);}

  /** indexed setter for dependencies - sets an indexed value - The dependency pairs for the answer.
   * @generated */
  public void setDependencies(int i, String v) { 
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies), i, v);}
  }

    