package com.onlineshopping.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.UserDto;
import com.onlineshopping.entity.Cart;
import com.onlineshopping.entity.User;
import com.onlineshopping.exceptions.UserNotFoundException;
import com.onlineshopping.iservice.IUserService;
import com.onlineshopping.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	// Validations are required
	@Transactional(readOnly = false)
	public boolean insertUser(User user) {
		user.setEmail(user.getEmail());
		User u = userRepository.save(user);
		return u != null;
	}

	@Transactional(readOnly = true)
	public String userLogin(String email, String password) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		if (user.isPresent() && user.get().getPassword().equals(password))
			return "Login Successful";
		throw new UserNotFoundException("User Not Found");
	}

	@Transactional(readOnly = true)
	public UserDto getUserProfileByEmail(String email) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		if (user.isPresent()) {
			User dbUser = user.get();
			return new UserDto(dbUser.getUserName(), dbUser.getEmail(), dbUser.getPassword(), dbUser.getPhonenumber(),
					dbUser.getAddress());

		} else
			throw new UserNotFoundException("User Not Found");
	}

	// validations are required
	@Transactional(readOnly = false)
	public String updateUserDetails(UserDto userDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (userOptional.isPresent()) {
			User userDb = userOptional.get();
			if (userDto.getUserName() != null) {
				userDb.setUserName(userDto.getUserName());
			}
			if (userDto.getPhonenumber() != 0) {
				userDb.setPhonenumber(userDto.getPhonenumber());
			}
			if (userDto.getPassword() != null) {
				userDb.setPassword(userDto.getPassword());
			}
			if (userDto.getAddress() != null) {
				userDb.setAddress(userDto.getAddress());
			}
			userRepository.save(userDb);
			return "Successfully Updated";
		}
		throw new UserNotFoundException("User Not Found");
	}

	// authentication to delete
	@Transactional(readOnly = false)
	public String deleteUser(String email) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		if (user.isPresent()) {
			User u = user.get();
			userRepository.delete(u);
			return "Account deleted successfully";
		}
		throw new UserNotFoundException("User Not Found");
	}
}
