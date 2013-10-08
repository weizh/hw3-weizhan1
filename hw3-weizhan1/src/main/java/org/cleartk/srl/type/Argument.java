

/* First created by JCasGen Tue Oct 08 12:26:59 EDT 2013 */
package org.cleartk.srl.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.score.type.ScoredAnnotation;


/** 
 * Updated by JCasGen Tue Oct 08 13:55:41 EDT 2013
 * XML source: /Users/indri/git/hw3-weizhan1/hw3-weizhan1/src/main/resources/hw3-weizhan1-aae-WithClearTK.xml
 * @generated */
public class Argument extends ScoredAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Argument.class);
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
  protected Argument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Argument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Argument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Argument(JCas jcas, int begin, int end) {
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
  //* Feature: annotation

  /** getter for annotation - gets 
   * @generated */
  public Annotation getAnnotation() {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_annotation == null)
      jcasType.jcas.throwFeatMissing("annotation", "org.cleartk.srl.type.Argument");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Argument_Type)jcasType).casFeatCode_annotation)));}
    
  /** setter for annotation - sets  
   * @generated */
  public void setAnnotation(Annotation v) {
    if (Argument_Type.featOkTst && ((Argument_Type)jcasType).casFeat_annotation == null)
      jcasType.jcas.throwFeatMissing("annotation", "org.cleartk.srl.type.Argument");
    jcasType.ll_cas.ll_setRefValue(addr, ((Argument_Type)jcasType).casFeatCode_annotation, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    