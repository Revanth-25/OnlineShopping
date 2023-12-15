package com.onlineshopping.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orederId;
	@Column(name="order_total")
	private double orderTotal;
	@Column(name="order_Date")
	private LocalDate orderDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Order() {
		
	}

	public Order(double orderTotal, LocalDate orderLocalDate) {
		super();
		this.orderTotal = orderTotal;
		this.orderDate = orderLocalDate;
	}

	public int getOrederId() {
		return orederId;
	}

	public void setOrederId(int orederId) {
		this.orederId = orederId;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(LocalDate orderLocalDate) {
		this.orderDate = orderLocalDate;
	}

	@Override
	public String toString() {
		return "Order [orederId=" + orederId + ", orderTotal=" + orderTotal + ", orderLocalDate=" + orderDate + "]";
	}

}
