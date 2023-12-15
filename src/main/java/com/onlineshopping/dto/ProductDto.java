package com.onlineshopping.dto;

import java.time.LocalDate;

public class ProductDto {
	
	private String userEmail;
	private int productId;
	private String prodName;
	private double prodPrice;
	private String prodDesc;
	private int prodStock;
	private LocalDate prodExPDate;
	private LocalDate prodManufDate;
	private int productQuantity;
	private double productTotal;
	private String categoryName;
	
	public ProductDto() {
		
	}

	public ProductDto(int productId, String prodName, double prodPrice, String prodDesc, int prodStock,
			LocalDate prodExPDate, LocalDate prodManufDate,String categoryName) {
		super();
		this.productId = productId;
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.prodDesc = prodDesc;
		this.prodStock = prodStock;
		this.prodExPDate = prodExPDate;
		this.prodManufDate = prodManufDate;
		this.categoryName=categoryName;
	}
	

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(double productTotal) {
		this.productTotal = productTotal;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public int getProdStock() {
		return prodStock;
	}

	public void setProdStock(int prodStock) {
		this.prodStock = prodStock;
	}

	public LocalDate getProdExPDate() {
		return prodExPDate;
	}

	public void setProdExPDate(LocalDate prodExPDate) {
		this.prodExPDate = prodExPDate;
	}

	public LocalDate getProdManufDate() {
		return prodManufDate;
	}

	public void setProdManufDate(LocalDate prodManufDate) {
		this.prodManufDate = prodManufDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	

}
