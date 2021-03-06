package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.ProductRequest;

@Repository
public interface ProductRequestRepository extends MongoRepository<ProductRequest, Integer>{

}
