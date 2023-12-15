package com.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshopping.dto.UserDto;
import com.onlineshopping.serviceimpl.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public String addUserDetails(@RequestBody UserDto userDto) {
		userService.insertUser(userDto);
		return "Successfully Registred";
	}

	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.userLogin(userDto), HttpStatus.OK);
	}

	@GetMapping(path = "/viewProfile", produces = "application/json")
	public ResponseEntity<UserDto> getUserProfileByEmail(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.getUserProfileByEmail(userDto.getUserEmail()), HttpStatus.OK);
	}

	@PutMapping("/updateProfile")
	public ResponseEntity<String> updateUserDetails(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.updateUserDetails(userDto), HttpStatus.OK);
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.updatePassword(userDto), HttpStatus.OK);
	}

	@DeleteMapping("/deleteProfile")
	public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.deleteUser(userDto), HttpStatus.OK);
	}

}