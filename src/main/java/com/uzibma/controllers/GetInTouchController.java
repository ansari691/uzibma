package com.uzibma.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uzibma.entities.GetInTouch;
import com.uzibma.entities.Product;
import com.uzibma.repositories.getInTouchRepository;
import com.uzibma.services.SequenceGeneratorService;

@RestController
@RequestMapping("/api/getInTouch")
@CrossOrigin
public class GetInTouchController {
	
	@Autowired
	getInTouchRepository getInTouchRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping
	public int save(@RequestBody GetInTouch getInTouch) {
		
		int result = 0;
		
		try {
			getInTouch.setId(sequenceGeneratorService.generateSequence(GetInTouch.SEQUENCE_NAME));
			getInTouch = getInTouchRepository.save(getInTouch);
			result = getInTouch.getId();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	@GetMapping
	public List<GetInTouch> get(){
		return getInTouchRepository.findAll();
	}
	
	@DeleteMapping("{id}")
	public String deleteById(@PathVariable Integer id ){
	if(getInTouchRepository.existsById(id)) {
		getInTouchRepository.deleteById(id);
		return "success";
	}
	else {
		return "query not found";
	}
	
}
	
}
