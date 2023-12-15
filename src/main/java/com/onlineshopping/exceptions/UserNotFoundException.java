package com.onlineshopping.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	private String message;

	public UserNotFoundException(String message) {
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "UserNotFoundException [message=" + message + "]";
	}

}
