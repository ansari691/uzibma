package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.Testimonial;

@Repository
public interface TestimonialRepository extends MongoRepository<Testimonial, Integer> {

}
