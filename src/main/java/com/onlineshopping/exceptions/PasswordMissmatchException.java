package com.onlineshopping.exceptions;

public class PasswordMissmatchException extends RuntimeException{
	private String message;

	public PasswordMissmatchException(String message) {
		super();
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "PasswordMissmatchException [message=" + message + "]";
	}
	

}
