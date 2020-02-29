package com.uzibma.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.SliderImage;

@Repository
public interface SliderImageRepository extends MongoRepository<SliderImage, Integer>{
	public List<SliderImage> findByActive(boolean active);
}
