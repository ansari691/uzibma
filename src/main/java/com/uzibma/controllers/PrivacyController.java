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

import com.uzibma.entities.Privacy;
import com.uzibma.repositories.PrivacyRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.vo.PrivacyVO;


@RestController
@RequestMapping("/api/privacy")
@CrossOrigin
public class PrivacyController {
	
	@Autowired
	PrivacyRepository privacyRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PutMapping
	public String updateEntity(@RequestBody PrivacyVO privacyVO) {
		String message = "success";
		Privacy privacy = null;
		Optional<Privacy> optional = privacyRepository.findById(1);
		if(optional.isPresent()){
			privacy = optional.get();
		}else {
			privacy = new Privacy();
			privacy.setId(1);
		}
		BeanUtils.copyProperties(privacyVO, privacy);
		privacyRepository.save(privacy);
		return message;
	}
	
	@GetMapping
	public Privacy get() {
		Privacy privacy = null;
		Optional<Privacy> optional = privacyRepository.findById(1);
		if(optional.isPresent()){
			privacy = optional.get();
		}
		return privacy;
	}
	
}
