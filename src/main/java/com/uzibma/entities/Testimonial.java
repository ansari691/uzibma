package com.uzibma.entities;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Testimonial {
	
	public final static String SEQUENCE_NAME = "testimonial_sequence";
	
	int id;
	Map<Integer, String> testimonialImageNames;
	int testimonialImageCount;
	String name;
	String message;
	boolean active;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Integer, String> getTestimonialImageNames() {
		return testimonialImageNames;
	}
	public void setTestimonialImageNames(Map<Integer, String> testimonialImageNames) {
		this.testimonialImageNames = testimonialImageNames;
	}
	public int getTestimonialImageCount() {
		return testimonialImageCount;
	}
	public void setTestimonialImageCount(int testimonialImageCount) {
		this.testimonialImageCount = testimonialImageCount;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
