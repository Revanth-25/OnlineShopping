package com.onlineshopping.exceptions;

public class ProductNotFoundException extends RuntimeException{

	private String message;

    public ProductNotFoundException(String message)
    {
    	this.message=message;
    }
    public String getMessage()
    {
    	return message;
    }
	@Override
	public String toString() {
		return "ProductNotFoundException [message=" + message + "]";
	}
    
}
