package com.onlineshopping.dto;

import com.onlineshopping.entity.Address;

public class UserDto {

	private String userName;
	private String userEmail;
	private String password;
	private long phonenumber;
	private Address address;
	

	public UserDto() {

	}

	public UserDto(String userName, String userEmail, String password,long phonenumber, Address address) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
		this.phonenumber=phonenumber;
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "UserDto [userName=" + userName + ", userEmail=" + userEmail + ", password=" + password + ", address="
				+ address + "]";
	}

}
