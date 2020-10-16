package com.uzibma.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uzibma.entities.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer>{

	public List<Product> findByTypeIgnoreCase(String type);

	public List<Product> findByTypeAndProductNameLikeIgnoreCase(String type, String searchTerm);

	public List<Product> findBySubTypeIgnoreCase(String subType);

	public Page<Product> findByType(Pageable paging, String productCategory);

	public long countByType(String productType);
	

}
