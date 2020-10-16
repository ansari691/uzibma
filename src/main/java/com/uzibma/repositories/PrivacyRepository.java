package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.Privacy;


@Repository
public interface PrivacyRepository extends MongoRepository<Privacy, Integer>{

}
