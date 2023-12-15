package com.onlineshopping.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Category;
import com.onlineshopping.entity.Product;
import com.onlineshopping.exceptions.CategoryNotFoundException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.exceptions.UserNotFoundException;
import com.onlineshopping.repository.CategoryRepository;
import com.onlineshopping.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<ProductDto> viewProducts() {
		List<Product> products = productRepository.findAll();

		List<ProductDto> productsDto = new ArrayList<>();
		for (Product prod : products) {
			productsDto.add(new ProductDto(prod.getProductId(), prod.getProductName(), prod.getProductPrice(),
					prod.getProductDescription(), prod.getProductStock(), prod.getProductExpiryDate(),
					prod.getProductManufactureDate(), prod.getCategory().getCategoryName()));
		}
		if (productsDto.isEmpty())
			throw new ProductNotFoundException("No Products to show");
		return productsDto;

	}

	@Transactional(readOnly = true)
	public List<ProductDto> viewByCategory(String categoryName) {
		Optional<Category> categoryOptional = categoryRepository.findByCategoryNameIgnoreCase(categoryName);
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();

			List<Product> products = productRepository.findByCategory(category);
			List<ProductDto> productDto = new ArrayList<ProductDto>();

			for (Product prod : products) {
				productDto.add(new ProductDto(prod.getProductId(), prod.getProductName(), prod.getProductPrice(),
						prod.getProductDescription(), prod.getProductStock(), prod.getProductExpiryDate(),
						prod.getProductManufactureDate(), prod.getCategory().getCategoryName()));
			}
			if (productDto.isEmpty()) 
				throw new ProductNotFoundException("No Products available in the category:" + categoryName);
			return productDto;
		}
		throw new CategoryNotFoundException("Category Not Available");
	}

	@Transactional(readOnly = true)
	public ProductDto viewProductByName(String productName) {
		Optional<Product> prodOptional = productRepository.findByProductNameIgnoreCase(productName);
		if (prodOptional.isPresent()) {
			Product prod = prodOptional.get();
			ProductDto prodDto = new ProductDto();
			prodDto.setProductId(prod.getProductId());
			prodDto.setProdName(prod.getProductName());
			prodDto.setProdDesc(prod.getProductDescription());
			prodDto.setProdPrice(prod.getProductPrice());
			prodDto.setProdStock(prod.getProductStock());
			prodDto.setProdManufDate(prod.getProductManufactureDate());
			prodDto.setProdExPDate(prod.getProductExpiryDate());
			prodDto.setCategoryName(prod.getCategory().getCategoryName());
			return prodDto;
		}
		throw new ProductNotFoundException("No Products available with the product name:"+productName);
	}
}
