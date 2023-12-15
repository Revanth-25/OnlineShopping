package com.onlineshopping.dto;

public class CartItemDto {
	
	    private int productQuantity;
	    private double productTotal;
	    private String productName;
	    
	    public CartItemDto() {
	    	
	    }

		public CartItemDto(int productQuantity, double productTotal, ProductDto productDto) {
			super();
			this.productQuantity = productQuantity;
			this.productTotal = productTotal;

		}	
		
		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
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
}
