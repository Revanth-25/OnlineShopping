package com.onlineshopping.exceptions;

public class OrderNotFoundException extends RuntimeException {

	private String message;

	public OrderNotFoundException(String message) {
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "OrderNotFoundException [message=" + message + "]";
	}

}
