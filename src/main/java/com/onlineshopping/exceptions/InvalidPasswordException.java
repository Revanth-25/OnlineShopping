package com.onlineshopping.exceptions;

public class InvalidPasswordException extends RuntimeException{
	
	private String message;

	public InvalidPasswordException(String message) {
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "InvalidPasswordException [message=" + message + "]";
	}
}
