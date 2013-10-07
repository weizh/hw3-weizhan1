
/* First created by JCasGen Sun Sep 22 22:11:00 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Sun Oct 06 21:09:52 EDT 2013
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.Token");
 
  /** @generated */
  final Feature casFeat_morph;
  /** @generated */
  final int     casFeatCode_morph;
  /** @generated */ 
  public String getMorph(int addr) {
        if (featOkTst && casFeat_morph == null)
      jcas.throwFeatMissing("morph", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_morph);
  }
  /** @generated */    
  public void setMorph(int addr, String v) {
        if (featOkTst && casFeat_morph == null)
      jcas.throwFeatMissing("morph", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_morph, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pos;
  /** @generated */
  final int     casFeatCode_pos;
  /** @generated */ 
  public String getPos(int addr) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pos);
  }
  /** @generated */    
  public void setPos(int addr, String v) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_pos, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ner;
  /** @generated */
  final int     casFeatCode_ner;
  /** @generated */ 
  public String getNer(int addr) {
        if (featOkTst && casFeat_ner == null)
      jcas.throwFeatMissing("ner", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ner);
  }
  /** @generated */    
  public void setNer(int addr, String v) {
        if (featOkTst && casFeat_ner == null)
      jcas.throwFeatMissing("ner", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_ner, v);}
    
  
 
  /** @generated */
  final Feature casFeat_token;
  /** @generated */
  final int     casFeatCode_token;
  /** @generated */ 
  public String getToken(int addr) {
        if (featOkTst && casFeat_token == null)
      jcas.throwFeatMissing("token", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_token);
  }
  /** @generated */    
  public void setToken(int addr, String v) {
        if (featOkTst && casFeat_token == null)
      jcas.throwFeatMissing("token", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_token, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_morph = jcas.getRequiredFeatureDE(casType, "morph", "uima.cas.String", featOkTst);
    casFeatCode_morph  = (null == casFeat_morph) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_morph).getCode();

 
    casFeat_pos = jcas.getRequiredFeatureDE(casType, "pos", "uima.cas.String", featOkTst);
    casFeatCode_pos  = (null == casFeat_pos) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pos).getCode();

 
    casFeat_ner = jcas.getRequiredFeatureDE(casType, "ner", "uima.cas.String", featOkTst);
    casFeatCode_ner  = (null == casFeat_ner) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ner).getCode();

 
    casFeat_token = jcas.getRequiredFeatureDE(casType, "token", "uima.cas.String", featOkTst);
    casFeatCode_token  = (null == casFeat_token) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_token).getCode();

  }
}



    