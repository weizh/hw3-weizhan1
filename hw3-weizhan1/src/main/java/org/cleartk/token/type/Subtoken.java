

/* First created by JCasGen Tue Oct 08 12:26:59 EDT 2013 */
package org.cleartk.token.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.cleartk.score.type.ScoredAnnotation;


/** 
 * Updated by JCasGen Tue Oct 08 13:52:38 EDT 2013
 * XML source: /Users/indri/git/hw3-weizhan1/hw3-weizhan1/src/main/resources/hw2-weizhan1-aae.xml
 * @generated */
public class Subtoken extends ScoredAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Subtoken.class);
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
  protected Subtoken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Subtoken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Subtoken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Subtoken(JCas jcas, int begin, int end) {
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
  //* Feature: position

  /** getter for position - gets 
   * @generated */
  public int getPosition() {
    if (Subtoken_Type.featOkTst && ((Subtoken_Type)jcasType).casFeat_position == null)
      jcasType.jcas.throwFeatMissing("position", "org.cleartk.token.type.Subtoken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Subtoken_Type)jcasType).casFeatCode_position);}
    
  /** setter for position - sets  
   * @generated */
  public void setPosition(int v) {
    if (Subtoken_Type.featOkTst && ((Subtoken_Type)jcasType).casFeat_position == null)
      jcasType.jcas.throwFeatMissing("position", "org.cleartk.token.type.Subtoken");
    jcasType.ll_cas.ll_setIntValue(addr, ((Subtoken_Type)jcasType).casFeatCode_position, v);}    
  }

    