package com.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshopping.dto.UserDto;
import com.onlineshopping.entity.User;
import com.onlineshopping.serviceimpl.UserService;



@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public String addUserDetails(@RequestBody User user) {
		userService.insertUser(user);
		return "Successfully Registred";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestParam("user_email")String email , @RequestParam("user_password")String password)
	{
		return new ResponseEntity<String>(userService.userLogin(email, password),HttpStatus.OK);
	}

	@GetMapping(path ="/viewProfile",produces = "application/json")
	public ResponseEntity<UserDto> getUserProfileByEmail(@RequestBody UserDto userDto) 
	{
		System.out.println(userDto);
		return new ResponseEntity<>(userService.getUserProfileByEmail(userDto.getUserEmail()),HttpStatus.OK);
	}

	@PutMapping("/updateProfile")
	public ResponseEntity<String> updateUserDetails(@RequestBody UserDto userDto)
	{
		return new ResponseEntity<String>(userService.updateUserDetails(userDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProfile")
	public ResponseEntity<String> deleteUser(@RequestParam("user_email")String email)
	{
		return new ResponseEntity<String>(userService.deleteUser(email),HttpStatus.OK);
	}
	

}
