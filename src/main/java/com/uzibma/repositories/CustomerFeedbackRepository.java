package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.CustomerFeedback;

@Repository
public interface CustomerFeedbackRepository extends MongoRepository<CustomerFeedback, Integer>{

}
