
/* First created by JCasGen Sun Sep 22 22:11:00 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

/** 
 * Updated by JCasGen Sun Oct 06 21:09:52 EDT 2013
 * XML source: /Users/indri/git/hw2/hw2-weizhan1/src/main/resources/hw2-weizhan1-aae.xml
 * @generated */
public class Token extends Annotation implements Comparable{
  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);

  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int type = typeIndexID;

  /** @generated */
  @Override
  public int getTypeIndexID() {return typeIndexID;}
 
  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected Token() {/* intentionally empty block */}
    
  /**
   * Internal - constructor used by generator
   * 
   * @generated
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */
  public Token(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/* default - does nothing empty block */
  }

  // *--------------*
  // * Feature: morph

  /**
   * getter for morph - gets lemma of the current token
   * 
   * @generated
   */
  public String getMorph() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_morph == null)
      jcasType.jcas.throwFeatMissing("morph", "edu.cmu.deiis.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_morph);}
    
  /**
   * setter for morph - sets lemma of the current token
   * 
   * @generated
   */
  public void setMorph(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_morph == null)
      jcasType.jcas.throwFeatMissing("morph", "edu.cmu.deiis.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_morph, v);}    
   
    
  // *--------------*
  // * Feature: pos

  /**
   * getter for pos - gets The part of speech tag for the current token
   * 
   * @generated
   */
  public String getPos() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "edu.cmu.deiis.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_pos);}
    
  /**
   * setter for pos - sets The part of speech tag for the current token
   * 
   * @generated
   */
  public void setPos(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "edu.cmu.deiis.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_pos, v);}    
   
    
  // *--------------*
  // * Feature: ner

  /**
   * getter for ner - gets The ner tag for the current token
   * 
   * @generated
   */
  public String getNer() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_ner == null)
      jcasType.jcas.throwFeatMissing("ner", "edu.cmu.deiis.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_ner);}
    
  /**
   * setter for ner - sets The ner tag for the current token
   * 
   * @generated
   */
  public void setNer(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_ner == null)
      jcasType.jcas.throwFeatMissing("ner", "edu.cmu.deiis.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_ner, v);}    
   
    
  // *--------------*
  // * Feature: token

  /**
   * getter for token - gets The token, which could be different from the text span.
   * 
   * @generated
   */
  public String getToken() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "edu.cmu.deiis.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_token);}
    
  /**
   * setter for token - sets The token, which could be different from the text span.
   * 
   * @generated
   */
  public void setToken(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "edu.cmu.deiis.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_token, v);}    
              @Override
  public int compareTo(Object t) {
    if (this.getBegin() != ((Token) t).getBegin())
      return this.getBegin() - ((Token) t).getBegin();
    if (this.getEnd() != ((Token) t).getEnd())
      return this.getEnd() - ((Token) t).getEnd();
    return 0;
  }
}
