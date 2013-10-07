
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
public class Answer_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Answer_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Answer_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Answer(addr, Answer_Type.this);
  			   Answer_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Answer(addr, Answer_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Answer.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.Answer");
 
  /** @generated */
  final Feature casFeat_isCorrect;
  /** @generated */
  final int     casFeatCode_isCorrect;
  /** @generated */ 
  public boolean getIsCorrect(int addr) {
        if (featOkTst && casFeat_isCorrect == null)
      jcas.throwFeatMissing("isCorrect", "edu.cmu.deiis.types.Answer");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isCorrect);
  }
  /** @generated */    
  public void setIsCorrect(int addr, boolean v) {
        if (featOkTst && casFeat_isCorrect == null)
      jcas.throwFeatMissing("isCorrect", "edu.cmu.deiis.types.Answer");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isCorrect, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dependencies;
  /** @generated */
  final int     casFeatCode_dependencies;
  /** @generated */ 
  public int getDependencies(int addr) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dependencies);
  }
  /** @generated */    
  public void setDependencies(int addr, int v) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_dependencies, v);}
    
   /** @generated */
  public String getDependencies(int addr, int i) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i);
  }
   
  /** @generated */ 
  public void setDependencies(int addr, int i, String v) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.deiis.types.Answer");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencies), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Answer_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_isCorrect = jcas.getRequiredFeatureDE(casType, "isCorrect", "uima.cas.Boolean", featOkTst);
    casFeatCode_isCorrect  = (null == casFeat_isCorrect) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isCorrect).getCode();

 
    casFeat_dependencies = jcas.getRequiredFeatureDE(casType, "dependencies", "uima.cas.StringArray", featOkTst);
    casFeatCode_dependencies  = (null == casFeat_dependencies) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependencies).getCode();

  }
}



    