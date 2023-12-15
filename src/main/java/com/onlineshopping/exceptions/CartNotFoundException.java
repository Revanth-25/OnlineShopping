package com.onlineshopping.exceptions;

public class CartNotFoundException extends RuntimeException{
	
	private String message;
	
	public CartNotFoundException() {
		
	}
	public CartNotFoundException(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "CartNotFoundException [message=" + message + "]";
	}
	

}
