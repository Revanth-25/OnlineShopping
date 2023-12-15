package com.onlineshopping.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.UserDto;
import com.onlineshopping.entity.Cart;
import com.onlineshopping.entity.User;
import com.onlineshopping.exceptions.InvalidEmailException;
import com.onlineshopping.exceptions.InvalidPasswordException;
import com.onlineshopping.exceptions.PasswordMissmatchException;
import com.onlineshopping.exceptions.UserCreationException;
import com.onlineshopping.exceptions.UserNotFoundException;
import com.onlineshopping.iservice.IUserService;
import com.onlineshopping.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Transactional(readOnly = false)
	public String insertUser(UserDto userDto) {
		Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (optionalUser.isPresent()) {
			throw new UserCreationException("USER_ALREADY_EXISTS");
		} else {

			if (userDto.getUserName() == null || !userDto.getUserName().matches("^[a-zA-Z][\\sa-zA-Z0-9]+$")) {
				throw new UserCreationException("INVALID_USER_NAME");
			}
			if (userDto.getPassword() == null || !userDto.getPassword().matches("^[a-zA-Z0-9@_-]{7,15}$")) {
				throw new InvalidPasswordException("INVALID_PASSWORD");
			} else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
				throw new PasswordMissmatchException("PASSWORD_MISMATCH");
			}
			if (userDto.getUserEmail() == null
					|| !userDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
				throw new InvalidEmailException("INVALID_EMAIL");
			}
			if (!(Long.toString(userDto.getPhonenumber()).matches("^(?:\\+91|0)?[6789]\\d{9}$"))) {
				throw new UserCreationException("INVALID_MOBILE_NUMBER");
			}
			if (userDto.getUserType() == 0 || (userDto.getUserType() != 'A' && userDto.getUserType() != 'U')) {
				throw new UserCreationException("INVALID USER TYPE");
			}
		}
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getUserEmail());
		user.setPhonenumber(userDto.getPhonenumber());
		user.setPassword(userDto.getPassword());
		user.setUserType(userDto.getUserType());
		user.setAddress(userDto.getAddress());

		userRepository.save(user);

		return "Registration Successful";
	}

	@Transactional(readOnly = true)
	public String userLogin(UserDto userDto) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (user.isPresent()) {
			if (user.get().getPassword().equals(userDto.getPassword())) {
				return "Logged in successfully. Welcome " + user.get().getUserName();
			}
			throw new InvalidEmailException("INVALID PASSWORD");
		}
		throw new UserNotFoundException();
	}

	@Transactional(readOnly = true)
	public UserDto getUserProfileByEmail(String email) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		if (user.isPresent()) {
			User dbUser = user.get();
			UserDto userDto = new UserDto();
			userDto.setUserName(dbUser.getUserName());
			userDto.setUserEmail(dbUser.getEmail());
			userDto.setPassword(dbUser.getPassword());
			userDto.setPhonenumber(dbUser.getPhonenumber());
			userDto.setUserType(dbUser.getUserType());
			userDto.setAddress(dbUser.getAddress());

			return userDto;

		} else
			throw new UserNotFoundException();
	}

	@Transactional(readOnly = false)
	public String updateUserDetails(UserDto userDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (userOptional.isPresent()) {
			User userDb = userOptional.get();
			if (userDto.getUserName() != null) {
				if (userDto.getUserName().matches("^[a-zA-Z][\\sa-zA-Z0-9]+$")) {
					userDb.setUserName(userDto.getUserName());
				} else {
					throw new UserCreationException("INVALID_USER_NAME");
				}
			}
			if (userDto.getPhonenumber() != 0) {
					if (Long.toString(userDto.getPhonenumber()).matches("^(?:\\+91|0)?[6789]\\d{9}$")) {
				userDb.setPhonenumber(userDto.getPhonenumber());
			} else {
				throw new UserCreationException("INVALID_MOBILE_NUMBER");
			       }
			}
			if (userDto.getAddress() != null) {
				userDb.setAddress(userDto.getAddress());
			}
			userRepository.save(userDb);
			return "Successfully Updated Profile";
		}
		throw new UserNotFoundException();
	}

	@Transactional(readOnly = false)
	public String updatePassword(UserDto userDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getPassword().trim().equals(userDto.getOldPassword().trim())) {
				if (userDto.getPassword() != null && userDto.getConfirmPassword() != null
						&& userDto.getPassword().matches("^[a-zA-Z0-9@_-]{7,15}$")
						&& userDto.getPassword().equals(userDto.getConfirmPassword())) {
					user.setPassword(userDto.getPassword());
					return "Password Updated";
				}
				throw new InvalidPasswordException("INVALID PASSWORD . FAILED TO UPDATE PASSWORD");
			}
			throw new PasswordMissmatchException("OLD PASSWORD IS INCORRECT");
		}
		throw new UserNotFoundException();
	}

	@Transactional(readOnly = false)
	public String deleteUser(UserDto userDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if ((userDto.getPassword() != null) && (user.getPassword().equals(userDto.getPassword()))) {
				userRepository.delete(user);
				return "Account deleted successfully";
			}
			throw new InvalidPasswordException("INVALID PASSWORD . FAILED TO DELETE ACCOUNT");
		}
		throw new UserNotFoundException();
	}

}
