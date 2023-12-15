package com.onlineshopping.dto;

import java.util.List;

import com.onlineshopping.entity.CartItems;


public class CartDto {
	

	private List<CartItemDto> cartItems;
	private int cartQuantity;
	private double cartTotal;

	
	public CartDto() {
		
	}

	public CartDto(int cartQuantity, double cartTotal) {
		super();
		this.cartQuantity = cartQuantity;
		this.cartTotal = cartTotal;

	}

	public List<CartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}


	@Override
	public String toString() {
		return "CartDto [cartItems=" + cartItems + ", cartQuantity=" + cartQuantity + ", cartTotal=" + cartTotal
				+ "]";
	}

}
