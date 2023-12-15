package com.onlineshopping.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineshopping.entity.Address;

public class UserDto {

	private String userName;
	private String userEmail;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private char userType;
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

//	@JsonIgnore
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
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

	//@JsonIgnore
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
