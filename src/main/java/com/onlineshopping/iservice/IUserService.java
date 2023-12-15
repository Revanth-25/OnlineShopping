package com.onlineshopping.iservice;

import com.onlineshopping.dto.UserDto;
//import com.onlineshopping.entity.User;

public interface IUserService{

	UserDto getUserProfileByEmail(String email);
}
