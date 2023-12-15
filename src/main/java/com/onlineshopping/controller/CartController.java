package com.onlineshopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshopping.dto.CartDto;
import com.onlineshopping.dto.OrderDetailsDto;
import com.onlineshopping.dto.OrderDto;
import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Cart;
import com.onlineshopping.serviceimpl.CartService;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	
	@PostMapping("/addToCart")
	public ResponseEntity<String> addToCart(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<>(cartService.addToCart(productDto),HttpStatus.OK);
	}
	
	@GetMapping("/viewCart")
	public ResponseEntity<CartDto> viewCart(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<CartDto>(cartService.viewCart(productDto),HttpStatus.OK);
	}
	
	@PutMapping("/updateCart")
	public ResponseEntity<String> removeFromCart(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<>(cartService.updateCart(productDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/removeProduct")
	public ResponseEntity<String> deleteProduct(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<>(cartService.removeProductFromCart(productDto),HttpStatus.OK);
	}

	@GetMapping("/checkout")
	public ResponseEntity<String> cartCheckout(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<>(cartService.cartCheckout(productDto),HttpStatus.OK);
	}
	
	@GetMapping("/viewOrders")
	public ResponseEntity<List<OrderDto>> viewOrders(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<>(cartService.viewOrders(productDto),HttpStatus.OK);
	}
}
