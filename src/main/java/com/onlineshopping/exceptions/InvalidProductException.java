package com.onlineshopping.exceptions;

public class InvalidProductException extends RuntimeException{
	private String message;

	public InvalidProductException(String message) {
		super();
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "InvalidProductException [message=" + message + "]";
	}
	

}
