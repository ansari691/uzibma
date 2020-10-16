package com.uzibma.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CustomerFeedback {

	public final static String SEQUENCE_NAME = "customer_feedback_sequence";
	
	@Id
	int feedbackId;
	String customerName; 
	String customerEmail;
	String customerContact; 
	String productType; 
	String productSubType;
	
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubCategory) {
		this.productSubType = productSubCategory;
	}
	String feedback;
	
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productCategory) {
		this.productType = productCategory;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
