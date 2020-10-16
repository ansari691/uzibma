package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.ContactUs;

@Repository
public interface ContactUsRepository extends MongoRepository<ContactUs, Integer>{
	
}
