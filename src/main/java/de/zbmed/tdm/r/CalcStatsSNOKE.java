package de.zbmed.tdm.r;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * CalcStatsSNOKE
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CalcStatsSNOKE {
	Map <String, Integer>
		drugbank;
	
	Map <String, Integer> 
		epilont;
	
	Map <String, Integer> 
		epso;
	
	Map <String, Integer> 
		esso;
	
	Map <String, Integer> 
		mesh;
	
	String jac10_drugbank_mesh,
		jac10_epso_mesh,
		jac10_esso_mesh,
		jac10_epilont_mesh,
		jac10_epso_drugbank,
		jac10_esso_drugbank,
		jac10_epilont_drugbank,
		jac10_esso_epso,
		jac10_epilont_epso,
		jac10_esso_epilont;
	
	String jac100_drugbank_mesh,
		jac100_epso_mesh,
		jac100_esso_mesh,
		jac100_epilont_mesh,
		jac100_epso_drugbank,
		jac100_esso_drugbank,
		jac100_epilont_drugbank,
		jac100_esso_epso,
		jac100_epilont_epso,
		jac100_esso_epilont;
	
	String jac1000_drugbank_mesh,
		jac1000_epso_mesh,
		jac1000_esso_mesh,
		jac1000_epilont_mesh,
		jac1000_epso_drugbank,
		jac1000_esso_drugbank,
		jac1000_epilont_drugbank,
		jac1000_esso_epso,
		jac1000_epilont_epso,
		jac1000_esso_epilont;
	
	String jac10000_drugbank_mesh,
		jac10000_epso_mesh,
		jac10000_esso_mesh,
		jac10000_epilont_mesh,
		jac10000_epso_drugbank,
		jac10000_esso_drugbank,
		jac10000_epilont_drugbank,
		jac10000_esso_epso,
		jac10000_epilont_epso,
		jac10000_esso_epilont;
	
	String jac_drugbank_mesh,
		jac_epso_mesh,
		jac_esso_mesh,
		jac_epilont_mesh,
		jac_epso_drugbank,
		jac_esso_drugbank,
		jac_epilont_drugbank,
		jac_esso_epso,
		jac_epilont_epso,
		jac_esso_epilont;
	
	String dic10_drugbank_mesh,
		dic10_epso_mesh,
		dic10_esso_mesh,
		dic10_epilont_mesh,
		dic10_epso_drugbank,
		dic10_esso_drugbank,
		dic10_epilont_drugbank,
		dic10_esso_epso,
		dic10_epilont_epso,
		dic10_esso_epilont;
	
	String dic100_drugbank_mesh,
		dic100_epso_mesh,
		dic100_esso_mesh,
		dic100_epilont_mesh,
		dic100_epso_drugbank,
		dic100_esso_drugbank,
		dic100_epilont_drugbank,
		dic100_esso_epso,
		dic100_epilont_epso,
		dic100_esso_epilont;
	
	String dic1000_drugbank_mesh,
		dic1000_epso_mesh,
		dic1000_esso_mesh,
		dic1000_epilont_mesh,
		dic1000_epso_drugbank,
		dic1000_esso_drugbank,
		dic1000_epilont_drugbank,
		dic1000_esso_epso,
		dic1000_epilont_epso,
		dic1000_esso_epilont;
	
	String dic10000_drugbank_mesh,
		dic10000_epso_mesh,
		dic10000_esso_mesh,
		dic10000_epilont_mesh,
		dic10000_epso_drugbank,
		dic10000_esso_drugbank,
		dic10000_epilont_drugbank,
		dic10000_esso_epso,
		dic10000_epilont_epso,
		dic10000_esso_epilont;
	
	String dic_drugbank_mesh,
		dic_epso_mesh,
		dic_esso_mesh,
		dic_epilont_mesh,
		dic_epso_drugbank,
		dic_esso_drugbank,
		dic_epilont_drugbank,
		dic_esso_epso,
		dic_epilont_epso,
		dic_esso_epilont;
	
	String cos10_drugbank_mesh,
		cos10_epso_mesh,
		cos10_esso_mesh,
		cos10_epilont_mesh,
		cos10_epso_drugbank,
		cos10_esso_drugbank,
		cos10_epilont_drugbank,
		cos10_esso_epso,
		cos10_epilont_epso,
		cos10_esso_epilont;
	
	String cos100_drugbank_mesh,
		cos100_epso_mesh,
		cos100_esso_mesh,
		cos100_epilont_mesh,
		cos100_epso_drugbank,
		cos100_esso_drugbank,
		cos100_epilont_drugbank,
		cos100_esso_epso,
		cos100_epilont_epso,
		cos100_esso_epilont;
	
	String cos1000_drugbank_mesh,
		cos1000_epso_mesh,
		cos1000_esso_mesh,
		cos1000_epilont_mesh,
		cos1000_epso_drugbank,
		cos1000_esso_drugbank,
		cos1000_epilont_drugbank,
		cos1000_esso_epso,
		cos1000_epilont_epso,
		cos1000_esso_epilont;
	
	String cos10000_drugbank_mesh,
		cos10000_epso_mesh,
		cos10000_esso_mesh,
		cos10000_epilont_mesh,
		cos10000_epso_drugbank,
		cos10000_esso_drugbank,
		cos10000_epilont_drugbank,
		cos10000_esso_epso,
		cos10000_epilont_epso,
		cos10000_esso_epilont;
	
	public String getJac10_drugbank_mesh() {
		return jac10_drugbank_mesh;
	}

	public void setJac10_drugbank_mesh(String jac10_drugbank_mesh) {
		this.jac10_drugbank_mesh = jac10_drugbank_mesh;
	}

	public String getJac10_epso_mesh() {
		return jac10_epso_mesh;
	}

	public void setJac10_epso_mesh(String jac10_epso_mesh) {
		this.jac10_epso_mesh = jac10_epso_mesh;
	}

	public String getJac10_esso_mesh() {
		return jac10_esso_mesh;
	}

	public void setJac10_esso_mesh(String jac10_esso_mesh) {
		this.jac10_esso_mesh = jac10_esso_mesh;
	}

	public String getJac10_epilont_mesh() {
		return jac10_epilont_mesh;
	}

	public void setJac10_epilont_mesh(String jac10_epilont_mesh) {
		this.jac10_epilont_mesh = jac10_epilont_mesh;
	}

	public String getJac10_epso_drugbank() {
		return jac10_epso_drugbank;
	}

	public void setJac10_epso_drugbank(String jac10_epso_drugbank) {
		this.jac10_epso_drugbank = jac10_epso_drugbank;
	}

	public String getJac10_esso_drugbank() {
		return jac10_esso_drugbank;
	}

	public void setJac10_esso_drugbank(String jac10_esso_drugbank) {
		this.jac10_esso_drugbank = jac10_esso_drugbank;
	}

	public String getJac10_epilont_drugbank() {
		return jac10_epilont_drugbank;
	}

	public void setJac10_epilont_drugbank(String jac10_epilont_drugbank) {
		this.jac10_epilont_drugbank = jac10_epilont_drugbank;
	}

	public String getJac10_esso_epso() {
		return jac10_esso_epso;
	}

	public void setJac10_esso_epso(String jac10_esso_epso) {
		this.jac10_esso_epso = jac10_esso_epso;
	}

	public String getJac10_epilont_epso() {
		return jac10_epilont_epso;
	}

	public void setJac10_epilont_epso(String jac10_epilont_epso) {
		this.jac10_epilont_epso = jac10_epilont_epso;
	}

	public String getJac10_esso_epilont() {
		return jac10_esso_epilont;
	}

	public void setJac10_esso_epilont(String jac10_esso_epilont) {
		this.jac10_esso_epilont = jac10_esso_epilont;
	}

	public String getJac100_drugbank_mesh() {
		return jac100_drugbank_mesh;
	}

	public void setJac100_drugbank_mesh(String jac100_drugbank_mesh) {
		this.jac100_drugbank_mesh = jac100_drugbank_mesh;
	}

	public String getJac100_epso_mesh() {
		return jac100_epso_mesh;
	}

	public void setJac100_epso_mesh(String jac100_epso_mesh) {
		this.jac100_epso_mesh = jac100_epso_mesh;
	}

	public String getJac100_esso_mesh() {
		return jac100_esso_mesh;
	}

	public void setJac100_esso_mesh(String jac100_esso_mesh) {
		this.jac100_esso_mesh = jac100_esso_mesh;
	}

	public String getJac100_epilont_mesh() {
		return jac100_epilont_mesh;
	}

	public void setJac100_epilont_mesh(String jac100_epilont_mesh) {
		this.jac100_epilont_mesh = jac100_epilont_mesh;
	}

	public String getJac100_epso_drugbank() {
		return jac100_epso_drugbank;
	}

	public void setJac100_epso_drugbank(String jac100_epso_drugbank) {
		this.jac100_epso_drugbank = jac100_epso_drugbank;
	}

	public String getJac100_esso_drugbank() {
		return jac100_esso_drugbank;
	}

	public void setJac100_esso_drugbank(String jac100_esso_drugbank) {
		this.jac100_esso_drugbank = jac100_esso_drugbank;
	}

	public String getJac100_epilont_drugbank() {
		return jac100_epilont_drugbank;
	}

	public void setJac100_epilont_drugbank(String jac100_epilont_drugbank) {
		this.jac100_epilont_drugbank = jac100_epilont_drugbank;
	}

	public String getJac100_esso_epso() {
		return jac100_esso_epso;
	}

	public void setJac100_esso_epso(String jac100_esso_epso) {
		this.jac100_esso_epso = jac100_esso_epso;
	}

	public String getJac100_epilont_epso() {
		return jac100_epilont_epso;
	}

	public void setJac100_epilont_epso(String jac100_epilont_epso) {
		this.jac100_epilont_epso = jac100_epilont_epso;
	}

	public String getJac100_esso_epilont() {
		return jac100_esso_epilont;
	}

	public void setJac100_esso_epilont(String jac100_esso_epilont) {
		this.jac100_esso_epilont = jac100_esso_epilont;
	}

	public String getJac1000_drugbank_mesh() {
		return jac1000_drugbank_mesh;
	}

	public void setJac1000_drugbank_mesh(String jac1000_drugbank_mesh) {
		this.jac1000_drugbank_mesh = jac1000_drugbank_mesh;
	}

	public String getJac1000_epso_mesh() {
		return jac1000_epso_mesh;
	}

	public void setJac1000_epso_mesh(String jac1000_epso_mesh) {
		this.jac1000_epso_mesh = jac1000_epso_mesh;
	}

	public String getJac1000_esso_mesh() {
		return jac1000_esso_mesh;
	}

	public void setJac1000_esso_mesh(String jac1000_esso_mesh) {
		this.jac1000_esso_mesh = jac1000_esso_mesh;
	}

	public String getJac1000_epilont_mesh() {
		return jac1000_epilont_mesh;
	}

	public void setJac1000_epilont_mesh(String jac1000_epilont_mesh) {
		this.jac1000_epilont_mesh = jac1000_epilont_mesh;
	}

	public String getJac1000_epso_drugbank() {
		return jac1000_epso_drugbank;
	}

	public void setJac1000_epso_drugbank(String jac1000_epso_drugbank) {
		this.jac1000_epso_drugbank = jac1000_epso_drugbank;
	}

	public String getJac1000_esso_drugbank() {
		return jac1000_esso_drugbank;
	}

	public void setJac1000_esso_drugbank(String jac1000_esso_drugbank) {
		this.jac1000_esso_drugbank = jac1000_esso_drugbank;
	}

	public String getJac1000_epilont_drugbank() {
		return jac1000_epilont_drugbank;
	}

	public void setJac1000_epilont_drugbank(String jac1000_epilont_drugbank) {
		this.jac1000_epilont_drugbank = jac1000_epilont_drugbank;
	}

	public String getJac1000_esso_epso() {
		return jac1000_esso_epso;
	}

	public void setJac1000_esso_epso(String jac1000_esso_epso) {
		this.jac1000_esso_epso = jac1000_esso_epso;
	}

	public String getJac1000_epilont_epso() {
		return jac1000_epilont_epso;
	}

	public void setJac1000_epilont_epso(String jac1000_epilont_epso) {
		this.jac1000_epilont_epso = jac1000_epilont_epso;
	}

	public String getJac1000_esso_epilont() {
		return jac1000_esso_epilont;
	}

	public void setJac1000_esso_epilont(String jac1000_esso_epilont) {
		this.jac1000_esso_epilont = jac1000_esso_epilont;
	}

	public String getJac10000_drugbank_mesh() {
		return jac10000_drugbank_mesh;
	}

	public void setJac10000_drugbank_mesh(String jac10000_drugbank_mesh) {
		this.jac10000_drugbank_mesh = jac10000_drugbank_mesh;
	}

	public String getJac10000_epso_mesh() {
		return jac10000_epso_mesh;
	}

	public void setJac10000_epso_mesh(String jac10000_epso_mesh) {
		this.jac10000_epso_mesh = jac10000_epso_mesh;
	}

	public String getJac10000_esso_mesh() {
		return jac10000_esso_mesh;
	}

	public void setJac10000_esso_mesh(String jac10000_esso_mesh) {
		this.jac10000_esso_mesh = jac10000_esso_mesh;
	}

	public String getJac10000_epilont_mesh() {
		return jac10000_epilont_mesh;
	}

	public void setJac10000_epilont_mesh(String jac10000_epilont_mesh) {
		this.jac10000_epilont_mesh = jac10000_epilont_mesh;
	}

	public String getJac10000_epso_drugbank() {
		return jac10000_epso_drugbank;
	}

	public void setJac10000_epso_drugbank(String jac10000_epso_drugbank) {
		this.jac10000_epso_drugbank = jac10000_epso_drugbank;
	}

	public String getJac10000_esso_drugbank() {
		return jac10000_esso_drugbank;
	}

	public void setJac10000_esso_drugbank(String jac10000_esso_drugbank) {
		this.jac10000_esso_drugbank = jac10000_esso_drugbank;
	}

	public String getJac10000_epilont_drugbank() {
		return jac10000_epilont_drugbank;
	}

	public void setJac10000_epilont_drugbank(String jac10000_epilont_drugbank) {
		this.jac10000_epilont_drugbank = jac10000_epilont_drugbank;
	}

	public String getJac10000_esso_epso() {
		return jac10000_esso_epso;
	}

	public void setJac10000_esso_epso(String jac10000_esso_epso) {
		this.jac10000_esso_epso = jac10000_esso_epso;
	}

	public String getJac10000_epilont_epso() {
		return jac10000_epilont_epso;
	}

	public void setJac10000_epilont_epso(String jac10000_epilont_epso) {
		this.jac10000_epilont_epso = jac10000_epilont_epso;
	}

	public String getJac10000_esso_epilont() {
		return jac10000_esso_epilont;
	}

	public void setJac10000_esso_epilont(String jac10000_esso_epilont) {
		this.jac10000_esso_epilont = jac10000_esso_epilont;
	}

	public String getDic10_drugbank_mesh() {
		return dic10_drugbank_mesh;
	}

	public void setDic10_drugbank_mesh(String dic10_drugbank_mesh) {
		this.dic10_drugbank_mesh = dic10_drugbank_mesh;
	}

	public String getDic10_epso_mesh() {
		return dic10_epso_mesh;
	}

	public void setDic10_epso_mesh(String dic10_epso_mesh) {
		this.dic10_epso_mesh = dic10_epso_mesh;
	}

	public String getDic10_esso_mesh() {
		return dic10_esso_mesh;
	}

	public void setDic10_esso_mesh(String dic10_esso_mesh) {
		this.dic10_esso_mesh = dic10_esso_mesh;
	}

	public String getDic10_epilont_mesh() {
		return dic10_epilont_mesh;
	}

	public void setDic10_epilont_mesh(String dic10_epilont_mesh) {
		this.dic10_epilont_mesh = dic10_epilont_mesh;
	}

	public String getDic10_epso_drugbank() {
		return dic10_epso_drugbank;
	}

	public void setDic10_epso_drugbank(String dic10_epso_drugbank) {
		this.dic10_epso_drugbank = dic10_epso_drugbank;
	}

	public String getDic10_esso_drugbank() {
		return dic10_esso_drugbank;
	}

	public void setDic10_esso_drugbank(String dic10_esso_drugbank) {
		this.dic10_esso_drugbank = dic10_esso_drugbank;
	}

	public String getDic10_epilont_drugbank() {
		return dic10_epilont_drugbank;
	}

	public void setDic10_epilont_drugbank(String dic10_epilont_drugbank) {
		this.dic10_epilont_drugbank = dic10_epilont_drugbank;
	}

	public String getDic10_esso_epso() {
		return dic10_esso_epso;
	}

	public void setDic10_esso_epso(String dic10_esso_epso) {
		this.dic10_esso_epso = dic10_esso_epso;
	}

	public String getDic10_epilont_epso() {
		return dic10_epilont_epso;
	}

	public void setDic10_epilont_epso(String dic10_epilont_epso) {
		this.dic10_epilont_epso = dic10_epilont_epso;
	}

	public String getDic10_esso_epilont() {
		return dic10_esso_epilont;
	}

	public void setDic10_esso_epilont(String dic10_esso_epilont) {
		this.dic10_esso_epilont = dic10_esso_epilont;
	}

	public String getDic100_drugbank_mesh() {
		return dic100_drugbank_mesh;
	}

	public void setDic100_drugbank_mesh(String dic100_drugbank_mesh) {
		this.dic100_drugbank_mesh = dic100_drugbank_mesh;
	}

	public String getDic100_epso_mesh() {
		return dic100_epso_mesh;
	}

	public void setDic100_epso_mesh(String dic100_epso_mesh) {
		this.dic100_epso_mesh = dic100_epso_mesh;
	}

	public String getDic100_esso_mesh() {
		return dic100_esso_mesh;
	}

	public void setDic100_esso_mesh(String dic100_esso_mesh) {
		this.dic100_esso_mesh = dic100_esso_mesh;
	}

	public String getDic100_epilont_mesh() {
		return dic100_epilont_mesh;
	}

	public void setDic100_epilont_mesh(String dic100_epilont_mesh) {
		this.dic100_epilont_mesh = dic100_epilont_mesh;
	}

	public String getDic100_epso_drugbank() {
		return dic100_epso_drugbank;
	}

	public void setDic100_epso_drugbank(String dic100_epso_drugbank) {
		this.dic100_epso_drugbank = dic100_epso_drugbank;
	}

	public String getDic100_esso_drugbank() {
		return dic100_esso_drugbank;
	}

	public void setDic100_esso_drugbank(String dic100_esso_drugbank) {
		this.dic100_esso_drugbank = dic100_esso_drugbank;
	}

	public String getDic100_epilont_drugbank() {
		return dic100_epilont_drugbank;
	}

	public void setDic100_epilont_drugbank(String dic100_epilont_drugbank) {
		this.dic100_epilont_drugbank = dic100_epilont_drugbank;
	}

	public String getDic100_esso_epso() {
		return dic100_esso_epso;
	}

	public void setDic100_esso_epso(String dic100_esso_epso) {
		this.dic100_esso_epso = dic100_esso_epso;
	}

	public String getDic100_epilont_epso() {
		return dic100_epilont_epso;
	}

	public void setDic100_epilont_epso(String dic100_epilont_epso) {
		this.dic100_epilont_epso = dic100_epilont_epso;
	}

	public String getDic100_esso_epilont() {
		return dic100_esso_epilont;
	}

	public void setDic100_esso_epilont(String dic100_esso_epilont) {
		this.dic100_esso_epilont = dic100_esso_epilont;
	}

	public String getDic1000_drugbank_mesh() {
		return dic1000_drugbank_mesh;
	}

	public void setDic1000_drugbank_mesh(String dic1000_drugbank_mesh) {
		this.dic1000_drugbank_mesh = dic1000_drugbank_mesh;
	}

	public String getDic1000_epso_mesh() {
		return dic1000_epso_mesh;
	}

	public void setDic1000_epso_mesh(String dic1000_epso_mesh) {
		this.dic1000_epso_mesh = dic1000_epso_mesh;
	}

	public String getDic1000_esso_mesh() {
		return dic1000_esso_mesh;
	}

	public void setDic1000_esso_mesh(String dic1000_esso_mesh) {
		this.dic1000_esso_mesh = dic1000_esso_mesh;
	}

	public String getDic1000_epilont_mesh() {
		return dic1000_epilont_mesh;
	}

	public void setDic1000_epilont_mesh(String dic1000_epilont_mesh) {
		this.dic1000_epilont_mesh = dic1000_epilont_mesh;
	}

	public String getDic1000_epso_drugbank() {
		return dic1000_epso_drugbank;
	}

	public void setDic1000_epso_drugbank(String dic1000_epso_drugbank) {
		this.dic1000_epso_drugbank = dic1000_epso_drugbank;
	}

	public String getDic1000_esso_drugbank() {
		return dic1000_esso_drugbank;
	}

	public void setDic1000_esso_drugbank(String dic1000_esso_drugbank) {
		this.dic1000_esso_drugbank = dic1000_esso_drugbank;
	}

	public String getDic1000_epilont_drugbank() {
		return dic1000_epilont_drugbank;
	}

	public void setDic1000_epilont_drugbank(String dic1000_epilont_drugbank) {
		this.dic1000_epilont_drugbank = dic1000_epilont_drugbank;
	}

	public String getDic1000_esso_epso() {
		return dic1000_esso_epso;
	}

	public void setDic1000_esso_epso(String dic1000_esso_epso) {
		this.dic1000_esso_epso = dic1000_esso_epso;
	}

	public String getDic1000_epilont_epso() {
		return dic1000_epilont_epso;
	}

	public void setDic1000_epilont_epso(String dic1000_epilont_epso) {
		this.dic1000_epilont_epso = dic1000_epilont_epso;
	}

	public String getDic1000_esso_epilont() {
		return dic1000_esso_epilont;
	}

	public void setDic1000_esso_epilont(String dic1000_esso_epilont) {
		this.dic1000_esso_epilont = dic1000_esso_epilont;
	}

	public String getDic10000_drugbank_mesh() {
		return dic10000_drugbank_mesh;
	}

	public void setDic10000_drugbank_mesh(String dic10000_drugbank_mesh) {
		this.dic10000_drugbank_mesh = dic10000_drugbank_mesh;
	}

	public String getDic10000_epso_mesh() {
		return dic10000_epso_mesh;
	}

	public void setDic10000_epso_mesh(String dic10000_epso_mesh) {
		this.dic10000_epso_mesh = dic10000_epso_mesh;
	}

	public String getDic10000_esso_mesh() {
		return dic10000_esso_mesh;
	}

	public void setDic10000_esso_mesh(String dic10000_esso_mesh) {
		this.dic10000_esso_mesh = dic10000_esso_mesh;
	}

	public String getDic10000_epilont_mesh() {
		return dic10000_epilont_mesh;
	}

	public void setDic10000_epilont_mesh(String dic10000_epilont_mesh) {
		this.dic10000_epilont_mesh = dic10000_epilont_mesh;
	}

	public String getDic10000_epso_drugbank() {
		return dic10000_epso_drugbank;
	}

	public void setDic10000_epso_drugbank(String dic10000_epso_drugbank) {
		this.dic10000_epso_drugbank = dic10000_epso_drugbank;
	}

	public String getDic10000_esso_drugbank() {
		return dic10000_esso_drugbank;
	}

	public void setDic10000_esso_drugbank(String dic10000_esso_drugbank) {
		this.dic10000_esso_drugbank = dic10000_esso_drugbank;
	}

	public String getDic10000_epilont_drugbank() {
		return dic10000_epilont_drugbank;
	}

	public void setDic10000_epilont_drugbank(String dic10000_epilont_drugbank) {
		this.dic10000_epilont_drugbank = dic10000_epilont_drugbank;
	}

	public String getDic10000_esso_epso() {
		return dic10000_esso_epso;
	}

	public void setDic10000_esso_epso(String dic10000_esso_epso) {
		this.dic10000_esso_epso = dic10000_esso_epso;
	}

	public String getDic10000_epilont_epso() {
		return dic10000_epilont_epso;
	}

	public void setDic10000_epilont_epso(String dic10000_epilont_epso) {
		this.dic10000_epilont_epso = dic10000_epilont_epso;
	}

	public String getDic10000_esso_epilont() {
		return dic10000_esso_epilont;
	}

	public void setDic10000_esso_epilont(String dic10000_esso_epilont) {
		this.dic10000_esso_epilont = dic10000_esso_epilont;
	}

	public String getCos10_drugbank_mesh() {
		return cos10_drugbank_mesh;
	}

	public void setCos10_drugbank_mesh(String cos10_drugbank_mesh) {
		this.cos10_drugbank_mesh = cos10_drugbank_mesh;
	}

	public String getCos10_epso_mesh() {
		return cos10_epso_mesh;
	}

	public void setCos10_epso_mesh(String cos10_epso_mesh) {
		this.cos10_epso_mesh = cos10_epso_mesh;
	}

	public String getCos10_esso_mesh() {
		return cos10_esso_mesh;
	}

	public void setCos10_esso_mesh(String cos10_esso_mesh) {
		this.cos10_esso_mesh = cos10_esso_mesh;
	}

	public String getCos10_epilont_mesh() {
		return cos10_epilont_mesh;
	}

	public void setCos10_epilont_mesh(String cos10_epilont_mesh) {
		this.cos10_epilont_mesh = cos10_epilont_mesh;
	}

	public String getCos10_epso_drugbank() {
		return cos10_epso_drugbank;
	}

	public void setCos10_epso_drugbank(String cos10_epso_drugbank) {
		this.cos10_epso_drugbank = cos10_epso_drugbank;
	}

	public String getCos10_esso_drugbank() {
		return cos10_esso_drugbank;
	}

	public void setCos10_esso_drugbank(String cos10_esso_drugbank) {
		this.cos10_esso_drugbank = cos10_esso_drugbank;
	}

	public String getCos10_epilont_drugbank() {
		return cos10_epilont_drugbank;
	}

	public void setCos10_epilont_drugbank(String cos10_epilont_drugbank) {
		this.cos10_epilont_drugbank = cos10_epilont_drugbank;
	}

	public String getCos10_esso_epso() {
		return cos10_esso_epso;
	}

	public void setCos10_esso_epso(String cos10_esso_epso) {
		this.cos10_esso_epso = cos10_esso_epso;
	}

	public String getCos10_epilont_epso() {
		return cos10_epilont_epso;
	}

	public void setCos10_epilont_epso(String cos10_epilont_epso) {
		this.cos10_epilont_epso = cos10_epilont_epso;
	}

	public String getCos10_esso_epilont() {
		return cos10_esso_epilont;
	}

	public void setCos10_esso_epilont(String cos10_esso_epilont) {
		this.cos10_esso_epilont = cos10_esso_epilont;
	}

	public String getCos100_drugbank_mesh() {
		return cos100_drugbank_mesh;
	}

	public void setCos100_drugbank_mesh(String cos100_drugbank_mesh) {
		this.cos100_drugbank_mesh = cos100_drugbank_mesh;
	}

	public String getCos100_epso_mesh() {
		return cos100_epso_mesh;
	}

	public void setCos100_epso_mesh(String cos100_epso_mesh) {
		this.cos100_epso_mesh = cos100_epso_mesh;
	}

	public String getCos100_esso_mesh() {
		return cos100_esso_mesh;
	}

	public void setCos100_esso_mesh(String cos100_esso_mesh) {
		this.cos100_esso_mesh = cos100_esso_mesh;
	}

	public String getCos100_epilont_mesh() {
		return cos100_epilont_mesh;
	}

	public void setCos100_epilont_mesh(String cos100_epilont_mesh) {
		this.cos100_epilont_mesh = cos100_epilont_mesh;
	}

	public String getCos100_epso_drugbank() {
		return cos100_epso_drugbank;
	}

	public void setCos100_epso_drugbank(String cos100_epso_drugbank) {
		this.cos100_epso_drugbank = cos100_epso_drugbank;
	}

	public String getCos100_esso_drugbank() {
		return cos100_esso_drugbank;
	}

	public void setCos100_esso_drugbank(String cos100_esso_drugbank) {
		this.cos100_esso_drugbank = cos100_esso_drugbank;
	}

	public String getCos100_epilont_drugbank() {
		return cos100_epilont_drugbank;
	}

	public void setCos100_epilont_drugbank(String cos100_epilont_drugbank) {
		this.cos100_epilont_drugbank = cos100_epilont_drugbank;
	}

	public String getCos100_esso_epso() {
		return cos100_esso_epso;
	}

	public void setCos100_esso_epso(String cos100_esso_epso) {
		this.cos100_esso_epso = cos100_esso_epso;
	}

	public String getCos100_epilont_epso() {
		return cos100_epilont_epso;
	}

	public void setCos100_epilont_epso(String cos100_epilont_epso) {
		this.cos100_epilont_epso = cos100_epilont_epso;
	}

	public String getCos100_esso_epilont() {
		return cos100_esso_epilont;
	}

	public void setCos100_esso_epilont(String cos100_esso_epilont) {
		this.cos100_esso_epilont = cos100_esso_epilont;
	}

	public String getCos1000_drugbank_mesh() {
		return cos1000_drugbank_mesh;
	}

	public void setCos1000_drugbank_mesh(String cos1000_drugbank_mesh) {
		this.cos1000_drugbank_mesh = cos1000_drugbank_mesh;
	}

	public String getCos1000_epso_mesh() {
		return cos1000_epso_mesh;
	}

	public void setCos1000_epso_mesh(String cos1000_epso_mesh) {
		this.cos1000_epso_mesh = cos1000_epso_mesh;
	}

	public String getCos1000_esso_mesh() {
		return cos1000_esso_mesh;
	}

	public void setCos1000_esso_mesh(String cos1000_esso_mesh) {
		this.cos1000_esso_mesh = cos1000_esso_mesh;
	}

	public String getCos1000_epilont_mesh() {
		return cos1000_epilont_mesh;
	}

	public void setCos1000_epilont_mesh(String cos1000_epilont_mesh) {
		this.cos1000_epilont_mesh = cos1000_epilont_mesh;
	}

	public String getCos1000_epso_drugbank() {
		return cos1000_epso_drugbank;
	}

	public void setCos1000_epso_drugbank(String cos1000_epso_drugbank) {
		this.cos1000_epso_drugbank = cos1000_epso_drugbank;
	}

	public String getCos1000_esso_drugbank() {
		return cos1000_esso_drugbank;
	}

	public void setCos1000_esso_drugbank(String cos1000_esso_drugbank) {
		this.cos1000_esso_drugbank = cos1000_esso_drugbank;
	}

	public String getCos1000_epilont_drugbank() {
		return cos1000_epilont_drugbank;
	}

	public void setCos1000_epilont_drugbank(String cos1000_epilont_drugbank) {
		this.cos1000_epilont_drugbank = cos1000_epilont_drugbank;
	}

	public String getCos1000_esso_epso() {
		return cos1000_esso_epso;
	}

	public void setCos1000_esso_epso(String cos1000_esso_epso) {
		this.cos1000_esso_epso = cos1000_esso_epso;
	}

	public String getCos1000_epilont_epso() {
		return cos1000_epilont_epso;
	}

	public void setCos1000_epilont_epso(String cos1000_epilont_epso) {
		this.cos1000_epilont_epso = cos1000_epilont_epso;
	}

	public String getCos1000_esso_epilont() {
		return cos1000_esso_epilont;
	}

	public void setCos1000_esso_epilont(String cos1000_esso_epilont) {
		this.cos1000_esso_epilont = cos1000_esso_epilont;
	}

	public String getCos10000_drugbank_mesh() {
		return cos10000_drugbank_mesh;
	}

	public void setCos10000_drugbank_mesh(String cos10000_drugbank_mesh) {
		this.cos10000_drugbank_mesh = cos10000_drugbank_mesh;
	}

	public String getCos10000_epso_mesh() {
		return cos10000_epso_mesh;
	}

	public void setCos10000_epso_mesh(String cos10000_epso_mesh) {
		this.cos10000_epso_mesh = cos10000_epso_mesh;
	}

	public String getCos10000_esso_mesh() {
		return cos10000_esso_mesh;
	}

	public void setCos10000_esso_mesh(String cos10000_esso_mesh) {
		this.cos10000_esso_mesh = cos10000_esso_mesh;
	}

	public String getCos10000_epilont_mesh() {
		return cos10000_epilont_mesh;
	}

	public void setCos10000_epilont_mesh(String cos10000_epilont_mesh) {
		this.cos10000_epilont_mesh = cos10000_epilont_mesh;
	}

	public String getCos10000_epso_drugbank() {
		return cos10000_epso_drugbank;
	}

	public void setCos10000_epso_drugbank(String cos10000_epso_drugbank) {
		this.cos10000_epso_drugbank = cos10000_epso_drugbank;
	}

	public String getCos10000_esso_drugbank() {
		return cos10000_esso_drugbank;
	}

	public void setCos10000_esso_drugbank(String cos10000_esso_drugbank) {
		this.cos10000_esso_drugbank = cos10000_esso_drugbank;
	}

	public String getCos10000_epilont_drugbank() {
		return cos10000_epilont_drugbank;
	}

	public void setCos10000_epilont_drugbank(String cos10000_epilont_drugbank) {
		this.cos10000_epilont_drugbank = cos10000_epilont_drugbank;
	}

	public String getCos10000_esso_epso() {
		return cos10000_esso_epso;
	}

	public void setCos10000_esso_epso(String cos10000_esso_epso) {
		this.cos10000_esso_epso = cos10000_esso_epso;
	}

	public String getCos10000_epilont_epso() {
		return cos10000_epilont_epso;
	}

	public void setCos10000_epilont_epso(String cos10000_epilont_epso) {
		this.cos10000_epilont_epso = cos10000_epilont_epso;
	}

	public String getCos10000_esso_epilont() {
		return cos10000_esso_epilont;
	}

	public void setCos10000_esso_epilont(String cos10000_esso_epilont) {
		this.cos10000_esso_epilont = cos10000_esso_epilont;
	}

	String cos_drugbank_mesh,
		cos_epso_mesh,
		cos_esso_mesh,
		cos_epilont_mesh,
		cos_epso_drugbank,
		cos_esso_drugbank,
		cos_epilont_drugbank,
		cos_esso_epso,
		cos_epilont_epso,
		cos_esso_epilont;
	

	public String getDic_drugbank_mesh() {
		return dic_drugbank_mesh;
	}

	public void setDic_drugbank_mesh(String dic_drugbank_mesh) {
		this.dic_drugbank_mesh = dic_drugbank_mesh;
	}

	public String getDic_epso_mesh() {
		return dic_epso_mesh;
	}

	public void setDic_epso_mesh(String dic_epso_mesh) {
		this.dic_epso_mesh = dic_epso_mesh;
	}

	public String getDic_esso_mesh() {
		return dic_esso_mesh;
	}

	public void setDic_esso_mesh(String dic_esso_mesh) {
		this.dic_esso_mesh = dic_esso_mesh;
	}

	public String getDic_epilont_mesh() {
		return dic_epilont_mesh;
	}

	public void setDic_epilont_mesh(String dic_epilont_mesh) {
		this.dic_epilont_mesh = dic_epilont_mesh;
	}

	public String getDic_epso_drugbank() {
		return dic_epso_drugbank;
	}

	public void setDic_epso_drugbank(String dic_epso_drugbank) {
		this.dic_epso_drugbank = dic_epso_drugbank;
	}

	public String getDic_esso_drugbank() {
		return dic_esso_drugbank;
	}

	public void setDic_esso_drugbank(String dic_esso_drugbank) {
		this.dic_esso_drugbank = dic_esso_drugbank;
	}

	public String getDic_epilont_drugbank() {
		return dic_epilont_drugbank;
	}

	public void setDic_epilont_drugbank(String dic_epilont_drugbank) {
		this.dic_epilont_drugbank = dic_epilont_drugbank;
	}

	public String getDic_esso_epso() {
		return dic_esso_epso;
	}

	public void setDic_esso_epso(String dic_esso_epso) {
		this.dic_esso_epso = dic_esso_epso;
	}

	public String getDic_epilont_epso() {
		return dic_epilont_epso;
	}

	public void setDic_epilont_epso(String dic_epilont_epso) {
		this.dic_epilont_epso = dic_epilont_epso;
	}

	public String getDic_esso_epilont() {
		return dic_esso_epilont;
	}

	public void setDic_esso_epilont(String dic_esso_epilont) {
		this.dic_esso_epilont = dic_esso_epilont;
	}

	public String getCos_drugbank_mesh() {
		return cos_drugbank_mesh;
	}

	public void setCos_drugbank_mesh(String cos_drugbank_mesh) {
		this.cos_drugbank_mesh = cos_drugbank_mesh;
	}

	public String getCos_epso_mesh() {
		return cos_epso_mesh;
	}

	public void setCos_epso_mesh(String cos_epso_mesh) {
		this.cos_epso_mesh = cos_epso_mesh;
	}

	public String getCos_esso_mesh() {
		return cos_esso_mesh;
	}

	public void setCos_esso_mesh(String cos_esso_mesh) {
		this.cos_esso_mesh = cos_esso_mesh;
	}

	public String getCos_epilont_mesh() {
		return cos_epilont_mesh;
	}

	public void setCos_epilont_mesh(String cos_epilont_mesh) {
		this.cos_epilont_mesh = cos_epilont_mesh;
	}

	public String getCos_epso_drugbank() {
		return cos_epso_drugbank;
	}

	public void setCos_epso_drugbank(String cos_epso_drugbank) {
		this.cos_epso_drugbank = cos_epso_drugbank;
	}

	public String getCos_esso_drugbank() {
		return cos_esso_drugbank;
	}

	public void setCos_esso_drugbank(String cos_esso_drugbank) {
		this.cos_esso_drugbank = cos_esso_drugbank;
	}

	public String getCos_epilont_drugbank() {
		return cos_epilont_drugbank;
	}

	public void setCos_epilont_drugbank(String cos_epilont_drugbank) {
		this.cos_epilont_drugbank = cos_epilont_drugbank;
	}

	public String getCos_esso_epso() {
		return cos_esso_epso;
	}

	public void setCos_esso_epso(String cos_esso_epso) {
		this.cos_esso_epso = cos_esso_epso;
	}

	public String getCos_epilont_epso() {
		return cos_epilont_epso;
	}

	public void setCos_epilont_epso(String cos_epilont_epso) {
		this.cos_epilont_epso = cos_epilont_epso;
	}

	public String getCos_esso_epilont() {
		return cos_esso_epilont;
	}

	public void setCos_esso_epilont(String cos_esso_epilont) {
		this.cos_esso_epilont = cos_esso_epilont;
	}

	public String getJac_drugbank_mesh() {
		return jac_drugbank_mesh;
	}

	public void setJac_drugbank_mesh(String jac_drugbank_mesh) {
		this.jac_drugbank_mesh = jac_drugbank_mesh;
	}

	public String getJac_epso_mesh() {
		return jac_epso_mesh;
	}

	public void setJac_epso_mesh(String jac_epso_mesh) {
		this.jac_epso_mesh = jac_epso_mesh;
	}

	public String getJac_esso_mesh() {
		return jac_esso_mesh;
	}

	public void setJac_esso_mesh(String jac_esso_mesh) {
		this.jac_esso_mesh = jac_esso_mesh;
	}

	public String getJac_epilont_mesh() {
		return jac_epilont_mesh;
	}

	public void setJac_epilont_mesh(String jac_epilont_mesh) {
		this.jac_epilont_mesh = jac_epilont_mesh;
	}

	public String getJac_epso_drugbank() {
		return jac_epso_drugbank;
	}

	public void setJac_epso_drugbank(String jac_epso_drugbank) {
		this.jac_epso_drugbank = jac_epso_drugbank;
	}

	public String getJac_esso_drugbank() {
		return jac_esso_drugbank;
	}

	public void setJac_esso_drugbank(String jac_esso_drugbank) {
		this.jac_esso_drugbank = jac_esso_drugbank;
	}

	public String getJac_epilont_drugbank() {
		return jac_epilont_drugbank;
	}

	public void setJac_epilont_drugbank(String jac_epilont_drugbank) {
		this.jac_epilont_drugbank = jac_epilont_drugbank;
	}

	public String getJac_esso_epso() {
		return jac_esso_epso;
	}

	public void setJac_esso_epso(String jac_esso_epso) {
		this.jac_esso_epso = jac_esso_epso;
	}

	public String getJac_epilont_epso() {
		return jac_epilont_epso;
	}

	public void setJac_epilont_epso(String jac_epilont_epso) {
		this.jac_epilont_epso = jac_epilont_epso;
	}

	public String getJac_esso_epilont() {
		return jac_esso_epilont;
	}

	public void setJac_esso_epilont(String jac_esso_epilont) {
		this.jac_esso_epilont = jac_esso_epilont;
	}

	CalcStatsSNOKE () {
	}
	
	public Map <String, Integer> readCsv (String filename) {
		Map <String, Integer> m = new HashMap <String, Integer> ();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
		    String line =  null;
		    int counter = 0;
		    while((line=br.readLine())!=null){
		    	if (counter++ != 0) { 
		    		String lins[] = line.split(";");
		    		String k = lins[0].replaceAll("\"", "");;
		    		int v = Integer.parseInt(lins[1]);
		    		if (!k.equals("1")) {
		    			m.put(k,  v);
		    			//System.out.println(k + "\t" + v);
		    		}
		    	}
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.sortMap(m);
	}
	
	public void readCsvIntoMaps () {
		drugbank = this.readCsv("resources/rstats/drugbank.csv");
		epilont = this.readCsv("resources/rstats/epilont.csv");
		epso =  this.readCsv("resources/rstats/epso.csv");
		esso =  this.readCsv("resources/rstats/esso.csv");
		mesh =  this.readCsv("resources/rstats/mesh.csv");
	}
	
	public int compareTopkMaps (Map <String, Integer> map1, Map <String, Integer> map2, int topk) {
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mk2 = m2.keySet();
		Iterator <String> imk2 = mk2.iterator();
		int counter=0;
		int keysmk1inm2 = 0;
		while (imk1.hasNext() && counter++<topk) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1)) {
				keysmk1inm2++;
			}
		}
		
		return keysmk1inm2;
		
	}
	
	public void printMapStats (Map <String, Integer> map1, Map <String, Integer> map2, int topk) {
		double jaccard;
		double aunionb;
		double ainterb;
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mk2 = m2.keySet();
		Iterator <String> imk2 = mk2.iterator();
		int counter=0;
		int keysmk1inm2 = 0;
		while (imk1.hasNext() && counter++<topk) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1)) {
				keysmk1inm2++;
			}
		}
		ainterb = keysmk1inm2;
		aunionb = m1.size() + m2.size() - ainterb;
		jaccard = ainterb / aunionb;
		
		System.out.println("Size m1:\t" + m1.size());
		System.out.println("Size m2:\t" + m2.size());
		System.out.println("Intersection:\t" + ainterb);
		System.out.println("Union:\t" + aunionb);
		System.out.println("Jaccard:\t" + jaccard);
	}
	
	public String calcJaccard (Map <String, Integer> map1, Map <String, Integer> map2, int topk) {
		double jaccard;
		double aunionb;
		double ainterb;
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mk2 = m2.keySet();
		Iterator <String> imk2 = mk2.iterator();
		int counter=0;
		int keysmk1inm2 = 0;
		while (imk1.hasNext() && counter++<topk) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1)) {
				keysmk1inm2++;
			}
		}
		ainterb = keysmk1inm2;
		aunionb = m1.size() + m2.size() - ainterb;
		jaccard = ainterb / aunionb;
		//"%1$,.2f
		String s_jac = String.format(Locale.ENGLISH, "%06f", jaccard);
		return s_jac;
	}
	
	public String calcDice (Map <String, Integer> map1, Map <String, Integer> map2, int topk) {
		double dice;
		double ainterb;
		double suma, sumb;
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		
		suma = m1.size();
		sumb = m2.size();
		
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mk2 = m2.keySet();
		Iterator <String> imk2 = mk2.iterator();
		int counter=0;
		int keysmk1inm2 = 0;
		while (imk1.hasNext() && counter++<topk) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1)) {
				keysmk1inm2++;
			}
		}
		ainterb = keysmk1inm2;

		dice = 2*(ainterb / (suma+sumb));
		String s_dice = String.format(Locale.ENGLISH, "%06f", dice);
		return s_dice;
	}
	
	public String calcCosine (Map <String, Integer> map1, Map <String, Integer> map2, int topk) {
		double cosine;
		double ainterb;
		double suma, sumb;
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		
		suma = m1.size();
		sumb = m2.size();
		
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mk2 = m2.keySet();
		Iterator <String> imk2 = mk2.iterator();
		int counter=0;
		int keysmk1inm2 = 0;
		while (imk1.hasNext() && counter++<topk) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1)) {
				keysmk1inm2++;
			}
		}
		ainterb = keysmk1inm2;

		cosine = ainterb / (Math.pow(suma, 0.5)*Math.pow(sumb, 0.5));
		String s_cosine = String.format(Locale.ENGLISH, "%06f", cosine);
		return s_cosine;
	}
	
	public double calcTripleIntersection (Map <String, Integer> map1, Map <String, Integer> map2,  Map <String, Integer> map3, int topk) {
		double ainterb;
		double abinterc;
		Map <String, Integer> m1 = this.trimMapToSize(map1, topk);
		Map <String, Integer> m2 = this.trimMapToSize(map2, topk);
		Map <String, Integer> m3 = this.trimMapToSize(map3, topk);
		Set <String> mk1 = m1.keySet();
		Iterator <String> imk1 = mk1.iterator();
		Set <String> mkintersec = new HashSet<String> ();
		
		while (imk1.hasNext()) {
			String simk1 = imk1.next();
			if (m2.containsKey(simk1) && m3.containsKey(simk1)) {
				mkintersec.add(simk1);
			}
		}
		
		return mkintersec.size();
		
	}
	
	public Map <String, Integer> trimMapToSize (Map <String, Integer> m, int size) {
		Map <String, Integer> trimmedMap = new HashMap <String, Integer> ();
		Set <String> keys = m.keySet();
		Iterator <String> it = keys.iterator();
		int counter = 0;
		while (it.hasNext() && counter++<size) {
			String k = it.next();
			trimmedMap.put(k, m.get(k));
		}
		return this.sortMap(trimmedMap);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalcStatsSNOKE css = new CalcStatsSNOKE ();
		css.readCsvIntoMaps ();
		
		System.out.println("DrugBank and MeSH, Top10:\t" + css.compareTopkMaps(css.getDrugbank(), css.getMesh(), 10));
		css.setDic10_drugbank_mesh(css.calcDice(css.getDrugbank(), css.getMesh(), 10));
		css.setCos10_drugbank_mesh(css.calcCosine(css.getDrugbank(), css.getMesh(), 10));
		css.setJac10_drugbank_mesh(css.calcJaccard(css.getDrugbank(), css.getMesh(), 10));
		System.out.println("DrugBank and MeSH, Top100:\t" + css.compareTopkMaps(css.getDrugbank(), css.getMesh(), 100));
		css.setDic100_drugbank_mesh(css.calcDice(css.getDrugbank(), css.getMesh(), 100));
		css.setCos100_drugbank_mesh(css.calcCosine(css.getDrugbank(), css.getMesh(), 100));
		css.setJac100_drugbank_mesh(css.calcJaccard(css.getDrugbank(), css.getMesh(), 100));
		System.out.println("DrugBank and MeSH, Top1000:\t" + css.compareTopkMaps(css.getDrugbank(), css.getMesh(), 1000));
		css.setDic1000_drugbank_mesh(css.calcDice(css.getDrugbank(), css.getMesh(), 1000));
		css.setCos1000_drugbank_mesh(css.calcCosine(css.getDrugbank(), css.getMesh(), 1000));
		css.setJac1000_drugbank_mesh(css.calcJaccard(css.getDrugbank(), css.getMesh(), 1000));
		System.out.println("DrugBank and MeSH, Top10000:\t" + css.compareTopkMaps(css.getDrugbank(), css.getMesh(), 10000));
		css.setDic10000_drugbank_mesh(css.calcDice(css.getDrugbank(), css.getMesh(), 10000));
		css.setCos10000_drugbank_mesh(css.calcCosine(css.getDrugbank(), css.getMesh(), 10000));
		css.setJac10000_drugbank_mesh(css.calcJaccard(css.getDrugbank(), css.getMesh(), 10000));
		System.out.println("DrugBank and MeSH, Top100000:\t" + css.compareTopkMaps(css.getDrugbank(), css.getMesh(), 100000));
		css.setDic_drugbank_mesh(css.calcDice(css.getDrugbank(), css.getMesh(), 100000));
		css.setCos_drugbank_mesh(css.calcCosine(css.getDrugbank(), css.getMesh(), 100000));
		css.setJac_drugbank_mesh(css.calcJaccard(css.getDrugbank(), css.getMesh(), 100000));
		System.out.println("DrugBank and MeSH, Jaccard:\t" + css.getJac_drugbank_mesh());
		
		
		System.out.println("DrugBank and MeSH, Stats:\t");
		css.printMapStats(css.getDrugbank(), css.getMesh(), 100000);
		
		System.out.println("EpSO and MeSH, Top10:\t" + css.compareTopkMaps(css.getEpso(), css.getMesh(), 10));
		css.setDic10_epso_mesh(css.calcDice(css.getEpso(), css.getMesh(), 10));
		css.setCos10_epso_mesh(css.calcCosine(css.getEpso(), css.getMesh(), 10));
		css.setJac10_epso_mesh(css.calcJaccard(css.getEpso(), css.getMesh(), 10));
		System.out.println("EpSO and MeSH, Top100:\t" + css.compareTopkMaps(css.getEpso(), css.getMesh(), 100));
		css.setDic100_epso_mesh(css.calcDice(css.getEpso(), css.getMesh(), 100));
		css.setCos100_epso_mesh(css.calcCosine(css.getEpso(), css.getMesh(), 100));
		css.setJac100_epso_mesh(css.calcJaccard(css.getEpso(), css.getMesh(), 100));
		System.out.println("EpSO and MeSH, Top1000:\t" + css.compareTopkMaps(css.getEpso(), css.getMesh(), 1000));
		css.setDic1000_epso_mesh(css.calcDice(css.getEpso(), css.getMesh(), 1000));
		css.setCos1000_epso_mesh(css.calcCosine(css.getEpso(), css.getMesh(), 1000));
		css.setJac1000_epso_mesh(css.calcJaccard(css.getEpso(), css.getMesh(), 1000));
		System.out.println("EpSO and MeSH, Top10000:\t" + css.compareTopkMaps(css.getEpso(), css.getMesh(), 10000));
		css.setDic10000_epso_mesh(css.calcDice(css.getEpso(), css.getMesh(), 10000));
		css.setCos10000_epso_mesh(css.calcCosine(css.getEpso(), css.getMesh(), 10000));
		css.setJac10000_epso_mesh(css.calcJaccard(css.getEpso(), css.getMesh(), 10000));
		System.out.println("EpSO and MeSH, Top100000:\t" + css.compareTopkMaps(css.getEpso(), css.getMesh(), 100000));
		css.setDic_epso_mesh(css.calcDice(css.getEpso(), css.getMesh(), 100000));
		css.setCos_epso_mesh(css.calcCosine(css.getEpso(), css.getMesh(), 100000));
		css.setJac_epso_mesh(css.calcJaccard(css.getEpso(), css.getMesh(), 100000));
		System.out.println("EpSO and MeSH, Jaccard:\t" + css.getJac_epso_mesh());
		System.out.println("EpSO and MeSH, Stats:\t");
		css.printMapStats(css.getEpso(), css.getMesh(), 100000);
		
		System.out.println("ESSO and MeSH, Top10:\t" + css.compareTopkMaps(css.getEsso(), css.getMesh(), 10));
		css.setDic10_esso_mesh(css.calcDice(css.getEsso(), css.getMesh(), 10));
		css.setCos10_esso_mesh(css.calcCosine(css.getEsso(), css.getMesh(), 10));
		css.setJac10_esso_mesh(css.calcJaccard(css.getEsso(), css.getMesh(), 10));
		System.out.println("ESSO and MeSH, Top100:\t" + css.compareTopkMaps(css.getEsso(), css.getMesh(), 100));
		css.setDic100_esso_mesh(css.calcDice(css.getEsso(), css.getMesh(), 100));
		css.setCos100_esso_mesh(css.calcCosine(css.getEsso(), css.getMesh(), 100));
		css.setJac100_esso_mesh(css.calcJaccard(css.getEsso(), css.getMesh(), 100));
		System.out.println("ESSO and MeSH, Top1000:\t" + css.compareTopkMaps(css.getEsso(), css.getMesh(), 1000));
		css.setDic1000_esso_mesh(css.calcDice(css.getEsso(), css.getMesh(), 1000));
		css.setCos1000_esso_mesh(css.calcCosine(css.getEsso(), css.getMesh(), 1000));
		css.setJac1000_esso_mesh(css.calcJaccard(css.getEsso(), css.getMesh(), 1000));
		System.out.println("ESSO and MeSH, Top10000:\t" + css.compareTopkMaps(css.getEsso(), css.getMesh(), 10000));
		css.setDic10000_esso_mesh(css.calcDice(css.getEsso(), css.getMesh(), 10000));
		css.setCos10000_esso_mesh(css.calcCosine(css.getEsso(), css.getMesh(), 10000));
		css.setJac10000_esso_mesh(css.calcJaccard(css.getEsso(), css.getMesh(), 10000));
		System.out.println("ESSO and MeSH, Top100000:\t" + css.compareTopkMaps(css.getEsso(), css.getMesh(), 100000));
		css.setDic_esso_mesh(css.calcDice(css.getEsso(), css.getMesh(), 100000));
		css.setCos_esso_mesh(css.calcCosine(css.getEsso(), css.getMesh(), 100000));
		css.setJac_esso_mesh(css.calcJaccard(css.getEsso(), css.getMesh(), 100000));
		System.out.println("ESSO and MeSH, Jaccard:\t" + css.getJac_esso_mesh());
		System.out.println("ESSO and MeSH, Stats:\t");
		css.printMapStats(css.getEsso(), css.getMesh(), 100000);
		
		System.out.println("EPILONT and MeSH, Top10:\t" + css.compareTopkMaps(css.getEpilont(), css.getMesh(), 10));
		css.setDic10_epilont_mesh(css.calcDice(css.getEpilont(), css.getMesh(), 10));
		css.setCos10_epilont_mesh(css.calcCosine(css.getEpilont(), css.getMesh(), 10));
		css.setJac10_epilont_mesh(css.calcJaccard(css.getEpilont(), css.getMesh(), 10));
		System.out.println("EPILONT and MeSH, Top100:\t" + css.compareTopkMaps(css.getEpilont(), css.getMesh(), 100));
		css.setDic100_epilont_mesh(css.calcDice(css.getEpilont(), css.getMesh(), 100));
		css.setCos100_epilont_mesh(css.calcCosine(css.getEpilont(), css.getMesh(), 100));
		css.setJac100_epilont_mesh(css.calcJaccard(css.getEpilont(), css.getMesh(), 100));
		System.out.println("EPILONT and MeSH, Top1000:\t" + css.compareTopkMaps(css.getEpilont(), css.getMesh(), 1000));
		css.setDic1000_epilont_mesh(css.calcDice(css.getEpilont(), css.getMesh(), 1000));
		css.setCos1000_epilont_mesh(css.calcCosine(css.getEpilont(), css.getMesh(), 1000));
		css.setJac1000_epilont_mesh(css.calcJaccard(css.getEpilont(), css.getMesh(), 1000));
		System.out.println("EPILONT and MeSH, Top10000:\t" + css.compareTopkMaps(css.getEpilont(), css.getMesh(), 10000));
		css.setDic10000_epilont_mesh(css.calcDice(css.getEpilont(), css.getMesh(), 10000));
		css.setCos10000_epilont_mesh(css.calcCosine(css.getEpilont(), css.getMesh(), 10000));
		css.setJac10000_epilont_mesh(css.calcJaccard(css.getEpilont(), css.getMesh(), 10000));
		System.out.println("EPILONT and MeSH, Top100000:\t" + css.compareTopkMaps(css.getEpilont(), css.getMesh(), 100000));
		css.setDic_epilont_mesh(css.calcDice(css.getEpilont(), css.getMesh(), 100000));
		css.setCos_epilont_mesh(css.calcCosine(css.getEpilont(), css.getMesh(), 100000));
		css.setJac_epilont_mesh(css.calcJaccard(css.getEpilont(), css.getMesh(), 100000));
		System.out.println("EPILONT and MeSH, Jaccard:\t" + css.getJac_epilont_mesh());
		System.out.println("EPILONT and MeSH, Stats:\t");
		css.printMapStats(css.getEpilont(), css.getMesh(), 100000);
		
		System.out.println("EpSO and DrugBank, Top10:\t" + css.compareTopkMaps(css.getEpso(), css.getDrugbank(), 10));
		css.setDic10_epso_drugbank(css.calcDice(css.getEpso(), css.getDrugbank(), 10));
		css.setCos10_epso_drugbank(css.calcCosine(css.getEpso(), css.getDrugbank(), 10));
		css.setJac10_epso_drugbank(css.calcJaccard(css.getEpso(), css.getDrugbank(), 10));
		System.out.println("EpSO and DrugBank, Top100:\t" + css.compareTopkMaps(css.getEpso(), css.getDrugbank(), 100));
		css.setDic100_epso_drugbank(css.calcDice(css.getEpso(), css.getDrugbank(), 100));
		css.setCos100_epso_drugbank(css.calcCosine(css.getEpso(), css.getDrugbank(), 100));
		css.setJac100_epso_drugbank(css.calcJaccard(css.getEpso(), css.getDrugbank(), 100));
		System.out.println("EpSO and DrugBank, Top1000:\t" + css.compareTopkMaps(css.getEpso(), css.getDrugbank(), 1000));
		css.setDic1000_epso_drugbank(css.calcDice(css.getEpso(), css.getDrugbank(), 1000));
		css.setCos1000_epso_drugbank(css.calcCosine(css.getEpso(), css.getDrugbank(), 1000));
		css.setJac1000_epso_drugbank(css.calcJaccard(css.getEpso(), css.getDrugbank(), 1000));
		System.out.println("EpSO and DrugBank, Top10000:\t" + css.compareTopkMaps(css.getEpso(), css.getDrugbank(), 10000));
		css.setDic10000_epso_drugbank(css.calcDice(css.getEpso(), css.getDrugbank(), 10000));
		css.setCos10000_epso_drugbank(css.calcCosine(css.getEpso(), css.getDrugbank(), 10000));
		css.setJac10000_epso_drugbank(css.calcJaccard(css.getEpso(), css.getDrugbank(), 10000));
		System.out.println("EpSO and DrugBank, Top100000:\t" + css.compareTopkMaps(css.getEpso(), css.getDrugbank(), 100000));
		css.setDic_epso_drugbank(css.calcDice(css.getEpso(), css.getDrugbank(), 100000));
		css.setCos_epso_drugbank(css.calcCosine(css.getEpso(), css.getDrugbank(), 100000));
		css.setJac_epso_drugbank(css.calcJaccard(css.getEpso(), css.getDrugbank(), 100000));
		System.out.println("EpSO and DrugBank, Jaccard:\t" + css.getJac_epso_drugbank());
		System.out.println("EpSO and DrugBank, Stats:\t");
		css.printMapStats(css.getEpso(), css.getDrugbank(), 100000);
		
		System.out.println("ESSO and DrugBank, Top10:\t" + css.compareTopkMaps(css.getEsso(), css.getDrugbank(), 10));
		css.setDic10_esso_drugbank(css.calcDice(css.getEsso(), css.getDrugbank(), 10));
		css.setCos10_esso_drugbank(css.calcCosine(css.getEsso(), css.getDrugbank(), 10));
		css.setJac10_esso_drugbank(css.calcJaccard(css.getEsso(), css.getDrugbank(), 10));
		System.out.println("ESSO and DrugBank, Top100:\t" + css.compareTopkMaps(css.getEsso(), css.getDrugbank(), 100));
		css.setDic100_esso_drugbank(css.calcDice(css.getEsso(), css.getDrugbank(), 100));
		css.setCos100_esso_drugbank(css.calcCosine(css.getEsso(), css.getDrugbank(), 100));
		css.setJac100_esso_drugbank(css.calcJaccard(css.getEsso(), css.getDrugbank(), 100));
		System.out.println("ESSO and DrugBank, Top1000:\t" + css.compareTopkMaps(css.getEsso(), css.getDrugbank(), 1000));
		css.setDic1000_esso_drugbank(css.calcDice(css.getEsso(), css.getDrugbank(), 1000));
		css.setCos1000_esso_drugbank(css.calcCosine(css.getEsso(), css.getDrugbank(), 1000));
		css.setJac1000_esso_drugbank(css.calcJaccard(css.getEsso(), css.getDrugbank(), 1000));
		System.out.println("ESSO and DrugBank, Top10000:\t" + css.compareTopkMaps(css.getEsso(), css.getDrugbank(), 10000));
		css.setDic10000_esso_drugbank(css.calcDice(css.getEsso(), css.getDrugbank(), 10000));
		css.setCos10000_esso_drugbank(css.calcCosine(css.getEsso(), css.getDrugbank(), 10000));
		css.setJac10000_esso_drugbank(css.calcJaccard(css.getEsso(), css.getDrugbank(), 10000));
		System.out.println("ESSO and DrugBank, Top100000:\t" + css.compareTopkMaps(css.getEsso(), css.getDrugbank(), 100000));
		css.setDic_esso_drugbank(css.calcDice(css.getEsso(), css.getDrugbank(), 100000));
		css.setCos_esso_drugbank(css.calcCosine(css.getEsso(), css.getDrugbank(), 100000));
		css.setJac_esso_drugbank(css.calcJaccard(css.getEsso(), css.getDrugbank(), 100000));
		System.out.println("ESSO and DrugBank, Jaccard:\t" + css.getJac_esso_drugbank());
		System.out.println("ESSO and DrugBank, Stats:\t");
		css.printMapStats(css.getEsso(), css.getDrugbank(), 100000);
		
		System.out.println("EPILONT and DrugBank, Top10:\t" + css.compareTopkMaps(css.getEpilont(), css.getDrugbank(), 10));
		css.setDic10_epilont_drugbank(css.calcDice(css.getEpilont(), css.getDrugbank(), 10));
		css.setCos10_epilont_drugbank(css.calcCosine(css.getEpilont(), css.getDrugbank(), 10));
		css.setJac10_epilont_drugbank(css.calcJaccard(css.getEpilont(), css.getDrugbank(), 10));
		System.out.println("EPILONT and DrugBank, Top100:\t" + css.compareTopkMaps(css.getEpilont(), css.getDrugbank(), 100));
		css.setDic100_epilont_drugbank(css.calcDice(css.getEpilont(), css.getDrugbank(), 100));
		css.setCos100_epilont_drugbank(css.calcCosine(css.getEpilont(), css.getDrugbank(), 100));
		css.setJac100_epilont_drugbank(css.calcJaccard(css.getEpilont(), css.getDrugbank(), 100));
		System.out.println("EPILONT and DrugBank, Top1000:\t" + css.compareTopkMaps(css.getEpilont(), css.getDrugbank(), 1000));
		css.setDic1000_epilont_drugbank(css.calcDice(css.getEpilont(), css.getDrugbank(), 1000));
		css.setCos1000_epilont_drugbank(css.calcCosine(css.getEpilont(), css.getDrugbank(), 1000));
		css.setJac1000_epilont_drugbank(css.calcJaccard(css.getEpilont(), css.getDrugbank(), 1000));
		System.out.println("EPILONT and DrugBank, Top10000:\t" + css.compareTopkMaps(css.getEpilont(), css.getDrugbank(), 10000));
		css.setDic10000_epilont_drugbank(css.calcDice(css.getEpilont(), css.getDrugbank(), 10000));
		css.setCos10000_epilont_drugbank(css.calcCosine(css.getEpilont(), css.getDrugbank(), 10000));
		css.setJac10000_epilont_drugbank(css.calcJaccard(css.getEpilont(), css.getDrugbank(), 10000));
		System.out.println("EPILONT and DrugBank, Top100000:\t" + css.compareTopkMaps(css.getEpilont(), css.getDrugbank(), 100000));
		css.setDic_epilont_drugbank(css.calcDice(css.getEpilont(), css.getDrugbank(), 100000));
		css.setCos_epilont_drugbank(css.calcCosine(css.getEpilont(), css.getDrugbank(), 100000));
		css.setJac_epilont_drugbank(css.calcJaccard(css.getEpilont(), css.getDrugbank(), 100000));
		System.out.println("EPILONT and DrugBank, Jaccard:\t" + css.getJac_epilont_drugbank());
		System.out.println("EPILONT and DrugBank, Stats:\t");
		css.printMapStats(css.getEpilont(), css.getDrugbank(), 100000);
		
		System.out.println("ESSO and EpSO, Top10:\t" + css.compareTopkMaps(css.getEsso(), css.getEpso(), 10));
		css.setDic10_esso_epso(css.calcDice(css.getEsso(), css.getEpso(), 10));
		css.setCos10_esso_epso(css.calcCosine(css.getEsso(), css.getEpso(), 10));
		css.setJac10_esso_epso(css.calcJaccard(css.getEsso(), css.getEpso(), 10));
		System.out.println("ESSO and EpSO, Top100:\t" + css.compareTopkMaps(css.getEsso(), css.getEpso(), 100));
		css.setDic100_esso_epso(css.calcDice(css.getEsso(), css.getEpso(), 100));
		css.setCos100_esso_epso(css.calcCosine(css.getEsso(), css.getEpso(), 100));
		css.setJac100_esso_epso(css.calcJaccard(css.getEsso(), css.getEpso(), 100));
		System.out.println("ESSO and EpSO, Top1000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpso(), 1000));
		css.setDic1000_esso_epso(css.calcDice(css.getEsso(), css.getEpso(), 1000));
		css.setCos1000_esso_epso(css.calcCosine(css.getEsso(), css.getEpso(), 1000));
		css.setJac1000_esso_epso(css.calcJaccard(css.getEsso(), css.getEpso(), 1000));
		System.out.println("ESSO and EpSO, Top10000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpso(), 10000));
		css.setDic10000_esso_epso(css.calcDice(css.getEsso(), css.getEpso(), 100000));
		css.setCos10000_esso_epso(css.calcCosine(css.getEsso(), css.getEpso(), 100000));
		css.setJac10000_esso_epso(css.calcJaccard(css.getEsso(), css.getEpso(), 100000));
		System.out.println("ESSO and EpSO, Top100000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpso(), 100000));
		css.setDic_esso_epso(css.calcDice(css.getEsso(), css.getEpso(), 100000));
		css.setCos_esso_epso(css.calcCosine(css.getEsso(), css.getEpso(), 100000));
		css.setJac_esso_epso(css.calcJaccard(css.getEsso(), css.getEpso(), 100000));
		System.out.println("ESSO and EpSO, Jaccard:\t" + css.getJac_esso_epso());
		System.out.println("ESSO and EpSO, Stats:\t");
		css.printMapStats(css.getEsso(), css.getEpso(), 100000);
		
		System.out.println("EPILONT and EpSO, Top10:\t" + css.compareTopkMaps(css.getEpilont(), css.getEpso(), 10));
		css.setDic10_epilont_epso(css.calcDice(css.getEpilont(), css.getEpso(), 10));
		css.setCos10_epilont_epso(css.calcCosine(css.getEpilont(), css.getEpso(), 10));
		css.setJac10_epilont_epso(css.calcJaccard(css.getEpilont(), css.getEpso(), 10));
		System.out.println("EPILONT and EpSO, Top100:\t" + css.compareTopkMaps(css.getEpilont(), css.getEpso(), 100));
		css.setDic100_epilont_epso(css.calcDice(css.getEpilont(), css.getEpso(), 100));
		css.setCos100_epilont_epso(css.calcCosine(css.getEpilont(), css.getEpso(), 100));
		css.setJac100_epilont_epso(css.calcJaccard(css.getEpilont(), css.getEpso(), 100));
		System.out.println("EPILONT and EpSO, Top1000:\t" + css.compareTopkMaps(css.getEpilont(), css.getEpso(), 1000));
		css.setDic1000_epilont_epso(css.calcDice(css.getEpilont(), css.getEpso(), 1000));
		css.setCos1000_epilont_epso(css.calcCosine(css.getEpilont(), css.getEpso(), 1000));
		css.setJac1000_epilont_epso(css.calcJaccard(css.getEpilont(), css.getEpso(), 1000));
		System.out.println("EPILONT and EpSO, Top10000:\t" + css.compareTopkMaps(css.getEpilont(), css.getEpso(), 10000));
		css.setDic10000_epilont_epso(css.calcDice(css.getEpilont(), css.getEpso(), 10000));
		css.setCos10000_epilont_epso(css.calcCosine(css.getEpilont(), css.getEpso(), 10000));
		css.setJac10000_epilont_epso(css.calcJaccard(css.getEpilont(), css.getEpso(), 10000));
		System.out.println("EPILONT and EpSO, Top100000:\t" + css.compareTopkMaps(css.getEpilont(), css.getEpso(), 100000));
		css.setDic_epilont_epso(css.calcDice(css.getEpilont(), css.getEpso(), 100000));
		css.setCos_epilont_epso(css.calcCosine(css.getEpilont(), css.getEpso(), 100000));
		css.setJac_epilont_epso(css.calcJaccard(css.getEpilont(), css.getEpso(), 100000));
		System.out.println("EPILONT and EpSO, Jaccard:\t" + css.getJac_epilont_epso());
		System.out.println("EPILONT and EpSO, Stats:\t");
		css.printMapStats(css.getEpilont(), css.getEpso(), 100000);
		
		System.out.println("ESSO and EPILONT, Top10:\t" + css.compareTopkMaps(css.getEsso(), css.getEpilont(), 10));
		css.setDic10_esso_epilont(css.calcDice(css.getEsso(), css.getEpilont(), 10));
		css.setCos10_esso_epilont(css.calcCosine(css.getEsso(), css.getEpilont(), 10));
		css.setJac10_esso_epilont(css.calcJaccard(css.getEsso(), css.getEpilont(), 10));
		System.out.println("ESSO and EPILONT, Top100:\t" + css.compareTopkMaps(css.getEsso(), css.getEpilont(), 100));
		css.setDic100_esso_epilont(css.calcDice(css.getEsso(), css.getEpilont(), 100));
		css.setCos100_esso_epilont(css.calcCosine(css.getEsso(), css.getEpilont(), 100));
		css.setJac100_esso_epilont(css.calcJaccard(css.getEsso(), css.getEpilont(), 100));
		System.out.println("ESSO and EPILONT, Top1000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpilont(), 1000));
		css.setDic1000_esso_epilont(css.calcDice(css.getEsso(), css.getEpilont(), 1000));
		css.setCos1000_esso_epilont(css.calcCosine(css.getEsso(), css.getEpilont(), 1000));
		css.setJac1000_esso_epilont(css.calcJaccard(css.getEsso(), css.getEpilont(), 1000));
		System.out.println("ESSO and EPILONT, Top10000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpilont(), 10000));
		css.setDic10000_esso_epilont(css.calcDice(css.getEsso(), css.getEpilont(), 10000));
		css.setCos10000_esso_epilont(css.calcCosine(css.getEsso(), css.getEpilont(), 10000));
		css.setJac10000_esso_epilont(css.calcJaccard(css.getEsso(), css.getEpilont(), 10000));
		System.out.println("ESSO and EPILONT, Top100000:\t" + css.compareTopkMaps(css.getEsso(), css.getEpilont(), 100000));
		css.setDic_esso_epilont(css.calcDice(css.getEsso(), css.getEpilont(), 100000));
		css.setCos_esso_epilont(css.calcCosine(css.getEsso(), css.getEpilont(), 100000));
		css.setJac_esso_epilont(css.calcJaccard(css.getEsso(), css.getEpilont(), 100000));
		System.out.println("ESSO and EPILONT, Jaccard:\t" + css.getJac_esso_epilont());
		System.out.println("ESSO and EPILONT, Stats:\t");
		css.printMapStats(css.getEsso(), css.getEpilont(), 100000);
		
		System.out.println("EpSO, ESSO, EPILONT intersection:\t" + css.calcTripleIntersection(css.getEpso(), css.getEsso(), css.getEpilont(), 100000));
		System.out.println("EpSO size:\t" + css.getEpso().size());
		System.out.println("ESSO size:\t" + css.getEsso().size());
		System.out.println("EPILONT size:\t" + css.getEpilont().size());
		
		css.printDiceTable();
		css.printCosineTable();
		css.printJaccardTable();
		
		css.writeDicePlotfileDrugBankMeSH();
		css.writeDicePlotfileEpSOMeSH();
		css.writeDicePlotfileESSOMeSH();
		css.writeDicePlotfileEPILONTMeSH();
		
		css.writeCosinePlotfileDrugBankMeSH();
		css.writeCosinePlotfileEpSOMeSH();
		css.writeCosinePlotfileESSOMeSH();
		css.writeCosinePlotfileEPILONTMeSH();
		
		css.writeJaccardPlotfileDrugBankMeSH();
		css.writeJaccardPlotfileEpSOMeSH();
		css.writeJaccardPlotfileESSOMeSH();
		css.writeJaccardPlotfileEPILONTMeSH();
	}
	
	public void printDiceTable () {
		String def_double = String.format("%06f", 2.);
		System.out.println("\\begin{table}[h!]");
		System.out.println("\\caption{Dice is the dice.}");
		System.out.println("      \\begin{tabular}{l|rrrrr}");
		System.out.println("         & \\textbf{MeSH}\t& \\textbf{DrugBank} \t& \\textbf{EpSO} \t& \\textbf{ESSO} \t& \\textbf{EPILONT} \\\\");
		System.out.println("\\hline");
		System.out.println("\\textbf{MeSH}     & "+def_double                 +"& "+this.getDic_drugbank_mesh()   +"& "+this.getDic_epso_mesh()    +"& "+this.getDic_esso_mesh()    +"& "+this.getDic_epilont_mesh()    +"\t \\\\");
		System.out.println("\\textbf{DrugBank} & "+this.getDic_drugbank_mesh()+"& "+def_double                    +"& "+this.getDic_epso_drugbank()+"& "+this.getDic_esso_drugbank()+"& "+this.getDic_epilont_drugbank()+"\t \\\\");
		System.out.println("\\textbf{EpSO}     & "+this.getDic_epso_mesh()    +"& "+this.getDic_epso_drugbank()   +"& "+def_double                 +"& "+this.getDic_esso_epso()    +"& "+this.getDic_epilont_epso()    +"\t \\\\");
		System.out.println("\\textbf{ESSO}     & "+this.getDic_esso_mesh()    +"& "+this.getDic_esso_drugbank()   +"& "+this.getDic_esso_epso()    +"& "+def_double                 +"& "+this.getDic_esso_epilont()    +"\t \\\\");
		System.out.println("\\textbf{EPILONT}  & "+this.getDic_epilont_mesh() +"& "+this.getDic_epilont_drugbank()+"& "+this.getDic_epilont_epso() +"& "+this.getDic_esso_epilont() +"& "+def_double                    +"\t \\\\");
		System.out.println("      \\end{tabular}");
		System.out.println("\\end{table}");
	}
	
	public void printCosineTable () {
		String def_double = String.format("%06f", 1.);
		System.out.println("\\begin{table}[h!]");
		System.out.println("\\caption{Cosine is the cosine.}");
		System.out.println("      \\begin{tabular}{l|rrrrr}");
		System.out.println("         & \\textbf{MeSH}\t& \\textbf{DrugBank} \t& \\textbf{EpSO} \t& \\textbf{ESSO} \t& \\textbf{EPILONT} \\\\");
		System.out.println("\\hline");
		System.out.println("\\textbf{MeSH}     & "+def_double                 +"& "+this.getCos_drugbank_mesh()   +"& "+this.getCos_epso_mesh()    +"& "+this.getCos_esso_mesh()    +"& "+this.getCos_epilont_mesh()    +"\t \\\\");
		System.out.println("\\textbf{DrugBank} & "+this.getCos_drugbank_mesh()+"& "+def_double                    +"& "+this.getCos_epso_drugbank()+"& "+this.getCos_esso_drugbank()+"& "+this.getCos_epilont_drugbank()+"\t \\\\");
		System.out.println("\\textbf{EpSO}     & "+this.getCos_epso_mesh()    +"& "+this.getCos_epso_drugbank()   +"& "+def_double                 +"& "+this.getCos_esso_epso()    +"& "+this.getCos_epilont_epso()    +"\t \\\\");
		System.out.println("\\textbf{ESSO}     & "+this.getCos_esso_mesh()    +"& "+this.getCos_esso_drugbank()   +"& "+this.getCos_esso_epso()    +"& "+def_double                 +"& "+this.getCos_esso_epilont()    +"\t \\\\");
		System.out.println("\\textbf{EPILONT}  & "+this.getCos_epilont_mesh() +"& "+this.getCos_epilont_drugbank()+"& "+this.getCos_epilont_epso() +"& "+this.getCos_esso_epilont() +"& "+def_double                    +"\t \\\\");
		System.out.println("      \\end{tabular}");
		System.out.println("\\end{table}");
	}
	
	public void printJaccardTable () {
		String def_double = String.format("%06f", 1.);
		System.out.println("\\begin{table}[h!]");
		System.out.println("\\caption{Jaccard is the jaccard.}");
		System.out.println("      \\begin{tabular}{l|rrrrr}");
		System.out.println("         & \\textbf{MeSH}\t& \\textbf{DrugBank} \t& \\textbf{EpSO} \t& \\textbf{ESSO} \t& \\textbf{EPILONT} \\\\");
		System.out.println("\\hline");
		System.out.println("\\textbf{MeSH}     & "+def_double                 +"& "+this.getJac_drugbank_mesh()   +"& "+this.getJac_epso_mesh()    +"& "+this.getJac_esso_mesh()    +"& "+this.getJac_epilont_mesh()    +"\t \\\\");
		System.out.println("\\textbf{DrugBank} & "+this.getJac_drugbank_mesh()+"& "+def_double                    +"& "+this.getJac_epso_drugbank()+"& "+this.getJac_esso_drugbank()+"& "+this.getJac_epilont_drugbank()+"\t \\\\");
		System.out.println("\\textbf{EpSO}     & "+this.getJac_epso_mesh()    +"& "+this.getJac_epso_drugbank()   +"& "+def_double                 +"& "+this.getJac_esso_epso()    +"& "+this.getJac_epilont_epso()    +"\t \\\\");
		System.out.println("\\textbf{ESSO}     & "+this.getJac_esso_mesh()    +"& "+this.getJac_esso_drugbank()   +"& "+this.getJac_esso_epso()    +"& "+def_double                 +"& "+this.getJac_esso_epilont()    +"\t \\\\");
		System.out.println("\\textbf{EPILONT}  & "+this.getJac_epilont_mesh() +"& "+this.getJac_epilont_drugbank()+"& "+this.getJac_epilont_epso() +"& "+this.getJac_esso_epilont() +"& "+def_double                    +"\t \\\\");
		System.out.println("      \\end{tabular}");
		System.out.println("\\end{table}");
	}

	public Map<String, Integer> getDrugbank() {
		return drugbank;
	}

	public void setDrugbank(Map<String, Integer> drugbank) {
		this.drugbank = drugbank;
	}

	public Map<String, Integer> getEpilont() {
		return epilont;
	}

	public void setEpilont(Map<String, Integer> epilont) {
		this.epilont = epilont;
	}

	public Map<String, Integer> getEpso() {
		return epso;
	}

	public void setEpso(Map<String, Integer> epso) {
		this.epso = epso;
	}

	public Map<String, Integer> getEsso() {
		return esso;
	}

	public void setEsso(Map<String, Integer> esso) {
		this.esso = esso;
	}

	public Map<String, Integer> getMesh() {
		return mesh;
	}

	public void setMesh(Map<String, Integer> mesh) {
		this.mesh = mesh;
	}

	public Map<String, Integer> sortMap(Map<String, Integer> m) {
		List<Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(m.entrySet());

		// sorting the list with a comparator
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// convert sortedMap back to Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public void writeDicePlotfileDrugBankMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/dice_drugbankmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Dice");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getDic10_drugbank_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getDic100_drugbank_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getDic1000_drugbank_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getDic10000_drugbank_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getDic_drugbank_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeDicePlotfileEpSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/dice_epsomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Dice");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getDic10_epso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getDic100_epso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getDic1000_epso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getDic10000_epso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getDic_epso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeDicePlotfileESSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/dice_essomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Dice");
			sb.append(delimiter);
			sb.append("DiceValue");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getDic10_esso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getDic100_esso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getDic1000_esso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getDic10000_esso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getDic_esso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeDicePlotfileEPILONTMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/dice_epilontmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Dice");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getDic10_epilont_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getDic100_epilont_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getDic1000_epilont_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getDic10000_epilont_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getDic_epilont_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

		// COSINE
	
	
	public void writeCosinePlotfileDrugBankMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/cosine_drugbankmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Cosine");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getCos10_drugbank_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getCos100_drugbank_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getCos1000_drugbank_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getCos10000_drugbank_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getCos_drugbank_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeCosinePlotfileEpSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/cosine_epsomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Cosine");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getCos10_epso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getCos100_epso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getCos1000_epso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getCos10000_epso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getCos_epso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeCosinePlotfileESSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/cosine_essomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Cosine");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getCos10_esso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getCos100_esso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getCos1000_esso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getCos10000_esso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getCos_esso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeCosinePlotfileEPILONTMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/cosine_epilontmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Cosine");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getCos10_epilont_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getCos100_epilont_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getCos1000_epilont_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getCos10000_epilont_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getCos_epilont_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// JACCARD
	
	public void writeJaccardPlotfileDrugBankMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/jaccard_drugbankmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Jaccard");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getJac10_drugbank_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getJac100_drugbank_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getJac1000_drugbank_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getJac10000_drugbank_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getJac_drugbank_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeJaccardPlotfileEpSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/jaccard_epsomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Jaccard");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getJac10_epso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getJac100_epso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getJac1000_epso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getJac10000_epso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getJac_epso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeJaccardPlotfileESSOMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/jaccard_essomesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Jaccard");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getJac10_esso_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getJac100_esso_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getJac1000_esso_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getJac10000_esso_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getJac_esso_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeJaccardPlotfileEPILONTMeSH() {
		String delimiter = "\t";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("resources/similarities/jaccard_epilontmesh.dat"));
			StringBuilder sb = new StringBuilder();
			sb.append("# Topk");
			sb.append(delimiter);
			sb.append("Jaccard");
			sb.append('\n');

			sb.append("10");
			sb.append(delimiter);
			sb.append(this.getJac10_epilont_mesh());
			sb.append('\n');

			sb.append("100");
			sb.append(delimiter);
			sb.append(this.getJac100_epilont_mesh());
			sb.append('\n');

			sb.append("1000");
			sb.append(delimiter);
			sb.append(this.getJac1000_epilont_mesh());
			sb.append('\n');

			sb.append("10000");
			sb.append(delimiter);
			sb.append(this.getJac10000_epilont_mesh());
			sb.append('\n');

			sb.append("100000");
			sb.append(delimiter);
			sb.append(this.getJac_epilont_mesh());
			sb.append('\n');

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
