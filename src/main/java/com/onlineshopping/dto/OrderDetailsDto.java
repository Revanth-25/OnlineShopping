package com.onlineshopping.dto;

public class OrderDetailsDto {

	 private int quantity;
	    private double total;
	    private String name;
	    
	    public OrderDetailsDto() {
	    	
	    }

		public OrderDetailsDto(int quantity, double total, String name) {
			super();
			this.quantity = quantity;
			this.total = total;
			this.name = name;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public double getTotal() {
			return total;
		}

		public void setTotal(double total) {
			this.total = total;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

}
