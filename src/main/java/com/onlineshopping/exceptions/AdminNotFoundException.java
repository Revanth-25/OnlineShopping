package com.onlineshopping.exceptions;

public class AdminNotFoundException extends RuntimeException{
	private String message;
	
	

	public AdminNotFoundException() {
		super();
	}


	public AdminNotFoundException(String message) {
		super();
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "AdminNotFoundException [message=" + message + "]";
	}
	

}
