package com.uzibma.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
	
	public final static String SEQUENCE_NAME = "product_sequence";

	int id;
	String productName;
	String type;
	String subType;
	String productDesc;
	double price;
	String briefDesc;
	ArrayList<String> productSize;
	String additionalInfo;
	Date productUpdateDate;
	boolean active;
	
	
	public String getBriefDesc() {
		return briefDesc;
	}

	public void setBriefDesc(String birefDesc) {
		this.briefDesc = birefDesc;
	}

	public ArrayList<String> getProductSize() {
		return productSize;
	}

	public void setProductSize(ArrayList<String> productSize) {
		this.productSize = productSize;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additonalInfo) {
		this.additionalInfo = additonalInfo;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	int productImageCount; 
	Map<Integer,String> productImageNames;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getProductUpdateDate() {
		return productUpdateDate;
	}

	public void setProductUpdateDate(Date productUpdateDate) {
		this.productUpdateDate = productUpdateDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getProductImageCount() {
		return productImageCount;
	}

	public void setProductImageCount(int productImageCount) {
		this.productImageCount = productImageCount;
	}

	public Map<Integer, String> getProductImageNames() {
		return productImageNames;
	}

	public void setProductImageNames(Map<Integer, String> productImageNames) {
		this.productImageNames = productImageNames;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDescrpition) {
		this.productDesc = productDescrpition;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
