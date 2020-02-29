package com.uzibma.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uzibma.entities.DatabaseSequence;
import com.uzibma.repositories.DatabaseSequenceRepository;
import com.uzibma.services.SequenceGeneratorService;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService{

	@Autowired
	DatabaseSequenceRepository databaseSequenceRepository;
	
	@Override
	public int generateSequence(String seqName) {
		DatabaseSequence databaseSequence;
		int returnValue;
		Optional<DatabaseSequence> optional = databaseSequenceRepository.findById(seqName);
		if(!optional.isPresent()) {
			databaseSequence = new DatabaseSequence();
			databaseSequence.setId(seqName);
			databaseSequence.setSeq(1);
			returnValue = 1; 
		}else {
			databaseSequence = optional.get();
			returnValue = databaseSequence.getSeq(); 
		}
		databaseSequence.setSeq(returnValue+1);
		databaseSequenceRepository.save(databaseSequence);
		
		return returnValue;
	}

	@Override
	public int getMaxSequenceNoForSequence(String seqName) {
		Optional<DatabaseSequence> optional = databaseSequenceRepository.findById(seqName);
		int result = 0;

		if(optional.isPresent()) {
			result = optional.get().getSeq();
		}
		return result;
	}

}
