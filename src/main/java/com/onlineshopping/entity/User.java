package com.onlineshopping.entity;

import java.util.List;
import java.util.Set;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
//import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	@Column(name="user_name")
	private String userName;
	@Column(name="user_password")
	private String password;
	@Column(name="user_email",unique = true , nullable = false)
	private String email;
	@Column(name="user_phonenumber")
	private long phonenumber;
	@Column(name="user_type")
	private char userType;
	
	@OneToMany
	@JoinColumn(name="user_id")
	private Set<Product> product;
	
    @OneToOne (cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name="address_id")
    private Address address;
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

//	@JsonIgnore
	@OneToMany (mappedBy="user" , fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_id")
	private List<Order> order;
	
	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

//	@JsonIgnore
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public User() {
		
	}

	public User(String userName, String password, String email, long phonenumber, char userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phonenumber = phonenumber;
		this.userType = userType;
//		this.cart=new Cart();
//		this.cart.setUser(this);
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", phonenumber=" + phonenumber + ", userType=" + userType + "]";
	}
	
}
