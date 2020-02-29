package com.uzibma.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uzibma.entities.CustomerFeedback;
import com.uzibma.repositories.CustomerFeedbackRepository;
import com.uzibma.services.SequenceGeneratorService;

@RestController
@RequestMapping("api/customerFeedbacks")
@CrossOrigin
public class CustomerFeedbackController {
	
	@Autowired
	CustomerFeedbackRepository customerFeedbackRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService; 

	@PostMapping
	public String addNewEntity(@RequestBody CustomerFeedback feedback) {
		feedback.setFeedbackId(sequenceGeneratorService.generateSequence(CustomerFeedback.SEQUENCE_NAME));
		customerFeedbackRepository.save(feedback);
		return "success";
	}
	
	@GetMapping
	public List<CustomerFeedback> getAll(){
		return customerFeedbackRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public CustomerFeedback findById(@PathVariable Integer id){
		Optional<CustomerFeedback> optional = customerFeedbackRepository.findById(id);
		CustomerFeedback customerFeedback = null;
		if(optional.isPresent()) {
			customerFeedback = optional.get();
		}
		return customerFeedback;
	}
}
