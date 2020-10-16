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

import com.uzibma.entities.Terms;
import com.uzibma.repositories.TermsRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.vo.TermsVO;


@RestController
@RequestMapping("api/terms")
@CrossOrigin
public class TermsController {
	
	@Autowired
	TermsRepository termsRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PutMapping
	public String updateEntity(@RequestBody TermsVO termsVO) {
		String message = "success";
		Terms terms = null;
		Optional<Terms> optional = termsRepository.findById(1);
		if(optional.isPresent()){
			terms = optional.get();
		}else {
			terms = new Terms();
			terms.setId(1);
		}
		BeanUtils.copyProperties(termsVO, terms);
		termsRepository.save(terms);
		return message;
	}
	
	@GetMapping
	public Terms get() {
		Terms terms = null;
		Optional<Terms> optional = termsRepository.findById(1);
		if(optional.isPresent()){
			terms = optional.get();
		}
		return terms;
	}
}
