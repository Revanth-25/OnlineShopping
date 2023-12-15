package com.onlineshopping.entity;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@Column(name="product_id")
	private int productId;
	@Column(name="product_name")
	private String productName;
	@Column(name="product_price")
	private double productPrice;
	@Column(name="product_desc")
	private String productDescription;
	@Column(name="product_stock")
	private int productStock;
	@Column(name="product_expiry_date")
	private LocalDate productExpiryDate;
	@Column(name="product_manufacture_date")
	private LocalDate productManufactureDate;
	
	@OneToMany(mappedBy = "product")
    private List<CartItems> cartItems = new ArrayList<>();
	
	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
	

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="category_id")
	Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails = new ArrayList<>();
	
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Product() {
		
	}

	public Product(int productId , String productName, double productPrice, String productDescription, int productStock,
			LocalDate productExpiryDate, LocalDate productManufactureDate) {
		super();
		this.productId=productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
		this.productStock = productStock;
		this.productExpiryDate = productExpiryDate;
		this.productManufactureDate = productManufactureDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public LocalDate getProductExpiryDate() {
		return productExpiryDate;
	}

	public void setProductExpiryDate(LocalDate productExpiryDate) {
		this.productExpiryDate = productExpiryDate;
	}

	public LocalDate getProductManufactureDate() {
		return productManufactureDate;
	}

	public void setProductManufactureDate(LocalDate productManufactureDate) {
		this.productManufactureDate = productManufactureDate;
	}
	

}
