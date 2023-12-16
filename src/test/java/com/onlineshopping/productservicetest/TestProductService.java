package com.onlineshopping.productservicetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Category;
import com.onlineshopping.entity.Product;
import com.onlineshopping.exceptions.CategoryNotFoundException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.repository.CategoryRepository;
import com.onlineshopping.repository.ProductRepository;
import com.onlineshopping.serviceimpl.ProductService;

@SpringBootTest
class TestProductService {
	
	@InjectMocks
	ProductService productService;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	CategoryRepository categoryRepository;
	
	
	@Test
	void viewProducts() {
		Product product1 = new Product(101,"Product1", 100.0, "Description1", 10, null, null, new Category("Category1"));
        Product product2 = new Product(102,"Product2", 200.0, "Description2", 20, null, null, new Category("Category2"));
        
        List<Product> mockProducts = List.of(product1, product2);
        
        Mockito.when(productRepository.findAll()).thenReturn(mockProducts);
        List<ProductDto> result = productService.viewProducts();
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());

        // You can add more detailed assertions for each ProductDto if needed
        Assertions.assertEquals("Product1", result.get(0).getProdName());
        Assertions.assertEquals("Product2", result.get(1).getProdName());
	}
	
	@Test
	void viewProducts_ProductNotFound() {      
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.viewProducts());
        Mockito.verify(productRepository, Mockito.times(1)).findAll();  
	}
	
	@Test
	void viewByCategory() {
		
		Category category = new Category(1, "Category1");
		Product product1 = new Product(101,"Product1", 100.0, "Description1", 10, null, null, new Category("Category1"));
        Product product2 = new Product(102,"Product2", 200.0, "Description2", 20, null, null, new Category("Category1"));
        
        List<Product> mockProducts = List.of(product1, product2);
        
        Mockito.when(categoryRepository.findByCategoryNameIgnoreCase("Category1")).thenReturn(Optional.of(category));
        Mockito.when(productRepository.findByCategory(category)).thenReturn(mockProducts);
        List<ProductDto> result = productService.viewByCategory(category.getCategoryName());
        Mockito.verify(categoryRepository, Mockito.times(1)).findByCategoryNameIgnoreCase("Category1");
        Mockito.verify(productRepository, Mockito.times(1)).findByCategory(category);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());

        // You can add more detailed assertions for each ProductDto if needed
        Assertions.assertEquals("Product1", result.get(0).getProdName());
        Assertions.assertEquals("Product2", result.get(1).getProdName());
	}
	
	@Test
	void viewByCategory_ProductNotFound() {
		
	    Category category = new Category(1, "Category1");
        Mockito.when(categoryRepository.findByCategoryNameIgnoreCase("Category1")).thenReturn(Optional.of(category));
        Mockito.when(productRepository.findByCategory(category)).thenReturn(new ArrayList<>());
        Assertions.assertThrows(ProductNotFoundException.class, ()-> productService.viewByCategory("Category1"));
	}
	
	@Test
	void viewByCategory_CategoryNotFound() {
		Assertions.assertThrows(CategoryNotFoundException.class, ()-> productService.viewByCategory("Category1"));
	}

	@Test
	void viewProductByName(){
		Product product1 = new Product(101,"Product1", 100.0, "Description1", 10, null, null, new Category("Category1"));
        
        Mockito.when(productRepository.findByProductNameIgnoreCase("Product1")).thenReturn(Optional.of(product1));
        ProductDto result = productService.viewProductByName("Product1");
        Mockito.verify(productRepository, Mockito.times(1)).findByProductNameIgnoreCase("Product1");
        Assertions.assertEquals("Product1", result.getProdName());

	}
	
	@Test
	void viewProductByName_ProductNotFound() {
		Assertions.assertThrows(ProductNotFoundException.class, ()-> productService.viewProductByName("Category1"));
	}

}
