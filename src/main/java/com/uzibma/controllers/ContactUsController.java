package com.uzibma.controllers;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uzibma.entities.ContactUs;
import com.uzibma.repositories.ContactUsRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.vo.ContactUsVO;

@RestController
@RequestMapping("api/contactUs")
@CrossOrigin
public class ContactUsController {
	
	@Autowired
	ContactUsRepository contactUsRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PutMapping
	public String updateEntity(@RequestBody ContactUsVO contactUsVO) {
		String message = "success";
		ContactUs contactUs = null;
		Optional<ContactUs> optional = contactUsRepository.findById(1);
		if(optional.isPresent()){
			contactUs = optional.get();
		}else {
			contactUs = new ContactUs();
			contactUs.setId(1);
		}
		BeanUtils.copyProperties(contactUsVO, contactUs);
		contactUsRepository.save(contactUs);
		return message;
	}
	
	@GetMapping
	public ContactUs get() {
		ContactUs contactUs = null;
		Optional<ContactUs> optional = contactUsRepository.findById(1);
		if(optional.isPresent()){
			contactUs = optional.get();
		}
		return contactUs;
	}
}
