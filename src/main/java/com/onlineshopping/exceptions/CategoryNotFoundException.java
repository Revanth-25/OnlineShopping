package com.onlineshopping.exceptions;

public class CategoryNotFoundException extends RuntimeException {
	private String message;

	public CategoryNotFoundException(String message) {
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "CategoryNotFoundException [message=" + message + "]";
	}
	
}
