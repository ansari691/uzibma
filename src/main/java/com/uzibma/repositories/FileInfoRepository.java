package com.uzibma.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.FileInfo;

@Repository
public interface FileInfoRepository extends MongoRepository<FileInfo,String>{
	
}
