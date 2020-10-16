package com.uzibma.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uzibma.entities.ProductRequest;
import com.uzibma.repositories.ProductRequestRepository;
import com.uzibma.services.SequenceGeneratorService;

@RestController
@RequestMapping("api/productRequests")
@CrossOrigin
public class ProductRequestController {
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	ProductRequestRepository productRequestRepository;
	
	@PostMapping
	public int save(@RequestBody ProductRequest productRequest) {
		int result = 0;
		try {
			productRequest.setRequestId(sequenceGeneratorService.generateSequence(ProductRequest.SEQUENCE_NAME));
			productRequest.setRequestDate(new Date());
			productRequest = productRequestRepository.save(productRequest);
			result = productRequest.getRequestId();
		} catch (Exception exception) {
		}
		return result;
	}
	
	@GetMapping
	public List<ProductRequest> getAll(){
		return productRequestRepository.findAll();
	}
	
	@PutMapping("/{id}/status/{status}")
	public String activate(@PathVariable Integer id, @PathVariable String status) {
		
		if(status.equals("activate") || status.equals("deactivate")) {
			Optional<ProductRequest> optional = productRequestRepository.findById(id);
			if(optional.isPresent()) {
				ProductRequest productRequest = optional.get();
				productRequest.setActive(status.equals("activate")?true : false);
				productRequestRepository.save(productRequest);
			}else return "entity not found";
		}else return "invalid status";
		
		return "success";
	}
	
}
