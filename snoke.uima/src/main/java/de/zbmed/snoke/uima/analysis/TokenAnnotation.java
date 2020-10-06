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
package de.zbmed.snoke.uima.analysis;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

/**
 * Custom TokenAnnotation for having a unique tag for tokens.
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TokenAnnotation extends uima.tt.TokenAnnotation {

  public final static int typeIndexID = JCasRegistry.register(TokenAnnotation.class);

  public final static int type = typeIndexID;

  public              int getTypeIndexID() {return typeIndexID;}

  /** Never called.  Disable default constructor
   *
   */

  protected TokenAnnotation() {}

  /** Internal - constructor used by generator
   *
   * @param addr
   * @param type
   */

  public TokenAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }

  public TokenAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 


  public TokenAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
   */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets text of token
   */
  public String getText() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets text of token 
   */
  public void setText(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: tokenType

  /** getter for tokenType - gets 
   */
  public int getTokenType() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenType == null)
      jcasType.jcas.throwFeatMissing("tokenType", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenType);}
    
  /** setter for tokenType - sets  
   */
  public void setTokenType(int v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenType == null)
      jcasType.jcas.throwFeatMissing("tokenType", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    jcasType.ll_cas.ll_setIntValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenType, v);}    
   
    
  //*--------------*
  //* Feature: tokenClass

  /** getter for tokenClass - gets semantic class, or other such classification of this token
   */
  public String getTokenClass() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenClass == null)
      jcasType.jcas.throwFeatMissing("tokenClass", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenClass);}
    
  /** setter for tokenClass - sets semantic class, or other such classification of this token 
   */
  public void setTokenClass(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenClass == null)
      jcasType.jcas.throwFeatMissing("tokenClass", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenClass, v);}   
  
  // *--------------*
  // * Feature: posTag

  /**
   * getter for posTag - gets contains part-of-speech of a corresponding token
   *
   */
  public String getPosTag() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type) jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr,
            ((TokenAnnotation_Type) jcasType).casFeatCode_posTag);
  }

  /**
   * setter for posTag - sets contains part-of-speech of a corresponding token
   *
   */
  public void setPosTag(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type) jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.zbmed.snoke.uima.analysis.TokenAnnotation");
    jcasType.ll_cas
            .ll_setStringValue(addr, ((TokenAnnotation_Type) jcasType).casFeatCode_posTag, v);
  }
  //*--------------*
  //* Feature: stem

  /** getter for stem - gets stem
   */
  public String getStem() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_stem == null)
      jcasType.jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_stem);}
    
  /** setter for stem - sets stem 
   */
  public void setStem(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_stem == null)
      jcasType.jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_stem, v);}    
  }


    