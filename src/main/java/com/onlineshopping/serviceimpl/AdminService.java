package com.onlineshopping.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Category;
import com.onlineshopping.entity.Product;
import com.onlineshopping.exceptions.CategoryNotFoundException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.repository.CategoryRepository;
import com.onlineshopping.repository.ProductRepository;

@Service
public class AdminService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = false)
	public String addProducts(Product product) {
		Optional<Category> catOptional = categoryRepository
				.findByCategoryNameIgnoreCase(product.getCategory().getCategoryName());
		if (catOptional.isPresent()) {
			Category category = catOptional.get();
			product.setCategory(category);
			productRepository.save(product);
		} else {
			productRepository.save(product);
		}
		return "Product added Successfully";
	}

	@Transactional(readOnly = false)
	public String updateProduct(ProductDto productDto) {
		Optional<Product> prodOptional = productRepository.findByProductNameIgnoreCase(productDto.getProdName());
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
				throw new CategoryNotFoundException("No category named: " + productDto.getCategoryName());
				}
			}
			productRepository.save(dbProduct);
			return "Product Updated";
		}
		throw new ProductNotFoundException("Product not found to Update");
	}

	@Transactional(readOnly = false)
	public String deleteProduct(String productName) {
		Optional<Product> prodOptional = productRepository.findByProductNameIgnoreCase(productName);
		if (prodOptional.isPresent()) {
			Product prod = prodOptional.get();
			productRepository.delete(prod);
			return "Product Deleted Sucessfully";
		}
		throw new ProductNotFoundException("Product not found to delete");
	}
}
