package com.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshopping.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	Optional<User> findByEmailIgnoreCase(String email);
	
}
