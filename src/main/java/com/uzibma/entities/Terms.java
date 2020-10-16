package com.uzibma.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Terms {
	String terms;
	Integer id;
	
	
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
