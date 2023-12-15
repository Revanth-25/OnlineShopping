package com.onlineshopping.exceptions;

public class InvalidEmailException extends RuntimeException {
	private String message;

	public InvalidEmailException(String message) {
		super();
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "InvalidEmailException [message=" + message + "]";
	}
}
