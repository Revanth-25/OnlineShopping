package com.onlineshopping.exceptions;

public class UserCreationException extends RuntimeException{
	
	private String message;
	
	public UserCreationException(String message)
	{
		this.message=message;
	}

	
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "UserCreationException [message=" + message + "]";
	}
}
