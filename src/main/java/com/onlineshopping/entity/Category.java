package com.onlineshopping.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
	private int categoryId;
    @Column(name = "category_name")
	private String categoryName;
    
    @OneToMany(mappedBy = "category") 
    private Set<Product> product;
	
	public Set<Product> getProd() {
		return product;
	}

	public void setProd(Set<Product> product) {
		this.product = product;
	}

	public Category() {
		
	}

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	
	public Category(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
