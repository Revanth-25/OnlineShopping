package com.onlineshopping.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Category;
import com.onlineshopping.entity.Product;
import com.onlineshopping.entity.User;
import com.onlineshopping.exceptions.AdminNotFoundException;
import com.onlineshopping.exceptions.CategoryNotFoundException;
import com.onlineshopping.exceptions.InvalidProductException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.repository.CategoryRepository;
import com.onlineshopping.repository.ProductRepository;
import com.onlineshopping.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = false)
	public String addProducts(ProductDto productDto) {

		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getUserType() == 'A') {

				if (productDto.getProductId() != 0 && productDto.getProdName() != null && productDto.getProdPrice() != 0
						&& productDto.getProdStock() != 0 && productDto.getProdDesc() != null
						&& productDto.getProdManufDate() != null && productDto.getCategoryName() != null) {
					Optional<Category> categoryOptional = categoryRepository
							.findByCategoryNameIgnoreCase(productDto.getCategoryName());
					if (categoryOptional.isPresent()) {
						Category category = categoryOptional.get();
						productDto.setCategoryName(category.getCategoryName());
						Product product = new Product();
						product.setProductId(productDto.getProductId());
						product.setProductName(productDto.getProdName());
						product.setProductPrice(productDto.getProdPrice());
						product.setProductStock(productDto.getProdStock());
						product.setProductDescription(productDto.getProdDesc());
						product.setProductManufactureDate(productDto.getProdManufDate());
						product.setProductExpiryDate(productDto.getProdExPDate());
						product.setCategory(category);
						productRepository.save(product);
					} else {
						Product product = new Product();
						product.setProductId(productDto.getProductId());
						product.setProductName(productDto.getProdName());
						product.setProductPrice(productDto.getProdPrice());
						product.setProductStock(productDto.getProdStock());
						product.setProductDescription(productDto.getProdDesc());
						product.setProductManufactureDate(productDto.getProdManufDate());
						product.setProductExpiryDate(productDto.getProdExPDate());
						product.getCategory().setCategoryName(productDto.getCategoryName());

						productRepository.save(product);
					}
					return "Product added Successfully";
				}
				throw new InvalidProductException("FAILED TO ADD PRODUCT.INVALID PRODUCT DETAILS");
			}
		}
		throw new AdminNotFoundException();
	}

	@Transactional(readOnly = false)
	public String updateProduct(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getUserType() == 'A') {
				Optional<Product> prodOptional = productRepository
						.findByProductNameIgnoreCase(productDto.getProdName());
				if (prodOptional.isPresent()) {
					Product dbProduct = prodOptional.get();

					if (productDto.getProdDesc() != null) {
						dbProduct.setProductDescription(productDto.getProdDesc());
					}
					if (productDto.getProdStock() != 0) {
						dbProduct.setProductStock(productDto.getProdStock());
					}
					if (productDto.getProdPrice() != 0) {
						dbProduct.setProductPrice(productDto.getProdPrice());
					}
					if (productDto.getProdExPDate() != null) {
						dbProduct.setProductExpiryDate(productDto.getProdExPDate());
					}
					if (productDto.getProdManufDate() != null) {
						dbProduct.setProductManufactureDate(productDto.getProdManufDate());
					}
					if (productDto.getCategoryName() != null) {
						Optional<Category> opCategory = categoryRepository
								.findByCategoryNameIgnoreCase(productDto.getCategoryName());

						if (opCategory.isPresent()) {
							dbProduct.setCategory(opCategory.get());
						} else {
							throw new CategoryNotFoundException(
									"NO CATEGORY FOUND WITH NAME :" + productDto.getCategoryName());
						}
					}
					productRepository.save(dbProduct);
					return "Product Updated";
				}
				throw new ProductNotFoundException("PRODUCT NOT FOUND");
			}
			throw new AdminNotFoundException("YOU ARE NOT AN ADMIN .PLEASE LOGIN AS ADMIN");
		}
		throw new AdminNotFoundException();
	}

	@Transactional(readOnly = false)
	public String deleteProduct(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getUserType() == 'A') {
				Optional<Product> prodOptional = productRepository
						.findByProductNameIgnoreCase(productDto.getProdName());
				if (prodOptional.isPresent()) {
					Product prod = prodOptional.get();
					productRepository.delete(prod);
					return "Product Deleted Sucessfully";
				}
				throw new ProductNotFoundException("PRODUCT NOT FOUND TO DELETE");
			}
			throw new AdminNotFoundException("YOU ARE NOT AN ADMIN .PLEASE LOGIN AS ADMIN");
		}
		throw new AdminNotFoundException();
	}
}
