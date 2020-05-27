/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.zbmed.tdm.uima.analysis;

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
 * Custom TokenAnnotation_Type for having a unique type of tag for tokens.
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TokenAnnotation_Type extends uima.tt.TokenAnnotation_Type {

  protected FSGenerator getFSGenerator() {return fsGenerator;}

  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TokenAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TokenAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TokenAnnotation(addr, TokenAnnotation_Type.this);
  			   TokenAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TokenAnnotation(addr, TokenAnnotation_Type.this);
  	  }
    };

  public final static int typeIndexID = TokenAnnotation.typeIndexID;

  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.zbmed.tdm.uima.analysis.TokenAnnotation");
 

  final Feature casFeat_text;

  final int     casFeatCode_text;

  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }

  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    

  final Feature casFeat_posTag;


  final int casFeatCode_posTag;
  

  public String getPosTag(int addr) {
    if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "org.apache.uima.TokenAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_posTag);
  }


  public void setPosTag(int addr, String v) {
    if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "org.apache.uima.TokenAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_posTag, v);
  }
 

  final Feature casFeat_tokenType;

  final int     casFeatCode_tokenType;

  public int getTokenType(int addr) {
        if (featOkTst && casFeat_tokenType == null)
      jcas.throwFeatMissing("tokenType", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tokenType);
  }

  public void setTokenType(int addr, int v) {
        if (featOkTst && casFeat_tokenType == null)
      jcas.throwFeatMissing("tokenType", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    ll_cas.ll_setIntValue(addr, casFeatCode_tokenType, v);}
    
  
 

  final Feature casFeat_tokenClass;

  final int     casFeatCode_tokenClass;

  public String getTokenClass(int addr) {
        if (featOkTst && casFeat_tokenClass == null)
      jcas.throwFeatMissing("tokenClass", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tokenClass);
  }

  public void setTokenClass(int addr, String v) {
        if (featOkTst && casFeat_tokenClass == null)
      jcas.throwFeatMissing("tokenClass", "de.zbmed.tdm.uima.analysis.TokenAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_tokenClass, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	*/
  public TokenAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_tokenType = jcas.getRequiredFeatureDE(casType, "tokenType", "uima.cas.Integer", featOkTst);
    casFeatCode_tokenType  = (null == casFeat_tokenType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenType).getCode();

 
    casFeat_tokenClass = jcas.getRequiredFeatureDE(casType, "tokenClass", "uima.cas.String", featOkTst);
    casFeatCode_tokenClass  = (null == casFeat_tokenClass) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenClass).getCode();

    casFeat_posTag = jcas.getRequiredFeatureDE(casType, "posTag", "uima.cas.String", featOkTst);
    casFeatCode_posTag = (null == casFeat_posTag) ? JCas.INVALID_FEATURE_CODE
            : ((FeatureImpl) casFeat_posTag).getCode();
    
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

    
    casFeat_stem = jcas.getRequiredFeatureDE(casType, "stem", "uima.cas.String", featOkTst);
    casFeatCode_stem  = (null == casFeat_stem) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_stem).getCode();

  }
  

  final Feature casFeat_stem;

  final int     casFeatCode_stem;

  public String getStem(int addr) {
        if (featOkTst && casFeat_stem == null)
      jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_stem);
  }

  public void setStem(int addr, String v) {
        if (featOkTst && casFeat_stem == null)
      jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_stem, v);}
    
  




}



    