package com.onlineshopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshopping.entity.Category;
import com.onlineshopping.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long>{
	
	Optional<Product> findByProductNameIgnoreCase(String name);
	List<Product> findByCategory(Category category);
}
