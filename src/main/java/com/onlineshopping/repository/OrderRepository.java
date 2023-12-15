package com.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlineshopping.entity.Order;
import com.onlineshopping.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Optional<Order> findByUser(User user);

}
