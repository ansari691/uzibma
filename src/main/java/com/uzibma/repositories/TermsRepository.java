package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.Terms;

@Repository
public interface TermsRepository extends MongoRepository<Terms, Integer> {

}
