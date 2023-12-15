package com.onlineshopping.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
	
	private double orderTotal;
	private LocalDate orderDate;
	private List<OrderDetailsDto> orderDetails;
	private List<OrderDto> orderDto;
	
	public OrderDto() {
		
	}
	public OrderDto(List<OrderDto> orderDto) {
		
	}

	public OrderDto(double orderTotal, LocalDate orderDate, List<OrderDetailsDto> orderDetails) {
		super();
		this.orderTotal = orderTotal;
		this.orderDate = orderDate;
		this.orderDetails = orderDetails;
	}
	
//	public List<OrderDto> getOrderDto() {
//		return orderDto;
//	}
//
//	public void setOrderDto(List<OrderDto> orderDto) {
//		this.orderDto = orderDto;
//	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderDetailsDto> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailsDto> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
