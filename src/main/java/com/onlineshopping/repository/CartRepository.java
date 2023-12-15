package com.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshopping.entity.Cart;
import com.onlineshopping.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer>
{

	Optional<Cart> findByUser(User user);
         
}
