package com.onlineshopping.handler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.onlineshopping.exceptions.AdminNotFoundException;
import com.onlineshopping.exceptions.CartNotFoundException;
import com.onlineshopping.exceptions.InvalidEmailException;
import com.onlineshopping.exceptions.InvalidPasswordException;
import com.onlineshopping.exceptions.OrderNotFoundException;
import com.onlineshopping.exceptions.PasswordMissmatchException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.exceptions.UserCreationException;
import com.onlineshopping.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	ApiError error = new ApiError();
	
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<Object> userCreationException(UserCreationException userEx)
	{
		error.setStatus(HttpStatus.ALREADY_REPORTED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(userEx.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Object> invalidPasswordException(InvalidPasswordException passEx)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(passEx.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Object> adminNotFoundExciption(AdminNotFoundException passEx)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(passEx.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(PasswordMissmatchException.class)
	public ResponseEntity<Object> passwordMissmatchException(PasswordMissmatchException passEx)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(passEx.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<Object> invalidEmailException(InvalidEmailException passInvalidEx)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(passInvalidEx.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException noUserException)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage("USER NOT FOUND");
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<Object> cartNotFoundException(CartNotFoundException excep)
	{
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(excep.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> productNotFoundException(ProductNotFoundException ex)
	{
		error.setStatus(HttpStatus.NO_CONTENT);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException exc)
	{
		error.setStatus(HttpStatus.NO_CONTENT);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException emptyInputException)
	{
		return new ResponseEntity<>("No Value Is Present In DB",HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return new ResponseEntity<>("Please Change HTTP Method Type",HttpStatus.NOT_FOUND);
	}

}
