package com.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshopping.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
}
