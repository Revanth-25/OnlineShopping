package com.onlineshopping.adminservicetest;

import java.time.LocalDate;
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
import com.onlineshopping.entity.User;
import com.onlineshopping.exceptions.AdminNotFoundException;
import com.onlineshopping.exceptions.InvalidProductException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.repository.CategoryRepository;
import com.onlineshopping.repository.ProductRepository;
import com.onlineshopping.repository.UserRepository;
import com.onlineshopping.serviceimpl.AdminService;

@SpringBootTest
class TestAdminService {

	@InjectMocks
	private AdminService adminService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Test
	void addProducts() {

		ProductDto productDto = new ProductDto();
		productDto.setProductId(665);
		productDto.setProdName("Iphone15");
		productDto.setProdPrice(70000);
		productDto.setProdStock(76);
		productDto.setProdDesc("Black color metalic body");
		LocalDate manufacturingDate = LocalDate.of(2023, 1, 1);
		productDto.setProdManufDate(manufacturingDate);
		productDto.setCategoryName("Mobiles");
		productDto.setUserEmail("test.com");
		Product product = new Product();
		product.setProductId(productDto.getProductId());
		product.setProductName(productDto.getProdName());
		product.setProductPrice(productDto.getProdPrice());
		product.setProductStock(productDto.getProdStock());
		product.setProductDescription(productDto.getProdDesc());
		product.setProductManufactureDate(productDto.getProdManufDate());
		Category category = new Category();
		category.setCategoryName(productDto.getCategoryName());
		product.setCategory(category);
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');

		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findByCategoryNameIgnoreCase(productDto.getCategoryName())).thenReturn(Optional.of(category));
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		String output = adminService.addProducts(productDto);
		Assertions.assertEquals("Product added Successfully", output);

	}

	@Test
	void addProducts_InvalidProduct() {
		ProductDto productDto = new ProductDto();
		productDto.setProductId(0);
		productDto.setProdName(null);
		productDto.setProdPrice(70000);
		productDto.setProdStock(76);
		productDto.setProdDesc("Black color metalic body");
		LocalDate manufacturingDate = LocalDate.of(2023, 1, 1);
		productDto.setProdManufDate(manufacturingDate);
		productDto.setCategoryName("Mobiles");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(InvalidProductException.class, () -> adminService.addProducts(productDto));
	}
	
	@Test
	void addProducts_AdminNotFound() {
		ProductDto productDto = new ProductDto();
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('U');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(AdminNotFoundException.class, () -> adminService.addProducts(productDto));
	}
	
	@Test
	void updateProducts() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setProdPrice(70000);
		productDto.setProdStock(76);
		productDto.setProdDesc("Black color metalic body");
		LocalDate manufacturingDate = LocalDate.of(2023, 1, 1);
		productDto.setProdManufDate(manufacturingDate);
		productDto.setCategoryName("Mobiles");
		productDto.setUserEmail("test.com");

		Product product = new Product();
		product.setProductName(productDto.getProdName());
		product.setProductPrice(productDto.getProdPrice());
		product.setProductStock(productDto.getProdStock());
		product.setProductDescription(productDto.getProdDesc());
		product.setProductManufactureDate(productDto.getProdManufDate());
		Category category = new Category();
		category.setCategoryName(productDto.getCategoryName());
		product.setCategory(category);
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');
		
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findByProductNameIgnoreCase(productDto.getProdName())).thenReturn(Optional.of(product));
		Mockito.when(categoryRepository.findByCategoryNameIgnoreCase(productDto.getCategoryName())).thenReturn(Optional.of(category));
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		String output = adminService.updateProduct(productDto);
		Assertions.assertEquals("Product Updated", output);	
	}
	@Test
	void updateProducts_ProductNotFound() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(ProductNotFoundException.class, ()->adminService.updateProduct(productDto));
	}
	
	@Test
	void updateProducts_AdminNotFound() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('U');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(AdminNotFoundException.class, ()->adminService.updateProduct(productDto));
	}
	
	@Test
	void deleteProduct() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setUserEmail("test.com");
		Product product = new Product();
		product.setProductName(productDto.getProdName());
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findByProductNameIgnoreCase(productDto.getProdName())).thenReturn(Optional.of(product));
		String output = adminService.deleteProduct(productDto);	
		// Verify that productRepository.delete(user) is called once
		Mockito.verify(productRepository, Mockito.times(1)).delete(product);
		Assertions.assertEquals("Product Deleted Sucessfully",output);
	}
	
	@Test
	void deleteProduct_ProductNotFound() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('A');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(ProductNotFoundException.class, ()-> adminService.deleteProduct(productDto));
	}
	
	@Test
	void deleteProduct_AdminNotFound() {
		ProductDto productDto = new ProductDto();
		productDto.setProdName("Iphone15");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		user.setUserType('U');
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Assertions.assertThrows(AdminNotFoundException.class, ()-> adminService.deleteProduct(productDto));
	}

}
