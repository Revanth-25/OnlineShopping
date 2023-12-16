package com.onlineshopping.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshopping.dto.CartDto;
import com.onlineshopping.dto.CartItemDto;
import com.onlineshopping.dto.OrderDetailsDto;
import com.onlineshopping.dto.OrderDto;
import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Cart;
import com.onlineshopping.entity.CartItems;
import com.onlineshopping.entity.Order;
import com.onlineshopping.entity.OrderDetails;
import com.onlineshopping.entity.Product;
import com.onlineshopping.entity.User;
import com.onlineshopping.exceptions.CartNotFoundException;
import com.onlineshopping.exceptions.OrderNotFoundException;
import com.onlineshopping.exceptions.ProductNotFoundException;
import com.onlineshopping.exceptions.UserNotFoundException;
import com.onlineshopping.repository.CartRepository;
import com.onlineshopping.repository.OrderRepository;
import com.onlineshopping.repository.ProductRepository;
import com.onlineshopping.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderRepository orderRepository;

	@Transactional(readOnly = false)
	public String addToCart(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<Product> prodOptional = productRepository.findById((long) productDto.getProductId());
			if (prodOptional.isPresent()) {
				Product product = prodOptional.get();
				Optional<Cart> existingCart = cartRepository.findByUser(user);
				if (existingCart.isPresent()) {
					Cart cart = existingCart.get();

					// Check if the product is already in the cart
					Optional<CartItems> existingCartItem = cart.getCartItems().stream()
							.filter(item -> item.getProduct().equals(product)).findFirst();

					if (existingCartItem.isPresent()) {
						// Update the existing cart item
						CartItems cartItem = existingCartItem.get();
						cartItem.setQuantity(cartItem.getQuantity() + productDto.getProdStock());
						cartItem.setTotal(
								cartItem.getTotal() + (productDto.getProdStock() * product.getProductPrice()));
					} else {
						// Create a new cart item
						CartItems cartItem = new CartItems();
						cartItem.setCart(cart);
						cartItem.setProduct(product);
						cartItem.setQuantity(productDto.getProdStock());
						cartItem.setTotal(productDto.getProdStock() * product.getProductPrice());
						cart.getCartItems().add(cartItem);
					}

					// Update the total and quantity in the cart
					cart.setCartQuantity(cart.getCartQuantity() + productDto.getProdStock());
					cart.setCartTotal(cart.getCartTotal() + (product.getProductPrice() * productDto.getProdStock()));

					cartRepository.save(cart);
					return "Product added to Cart";
				} else {

					// Create a new cart
					Cart cart = new Cart();
					cart.setUser(user);

					// Create a new cart item
					CartItems cartItem = new CartItems();
					cartItem.setCart(cart);
					cartItem.setProduct(product);
					cartItem.setQuantity(productDto.getProdStock());
					cartItem.setTotal(productDto.getProdStock() * product.getProductPrice());
					cart.getCartItems().add(cartItem);

					// Update the total and quantity in the cart
					cart.setCartQuantity(productDto.getProdStock());
					cart.setCartTotal(product.getProductPrice() * productDto.getProdStock());

					cartRepository.save(cart);
					return "Product added to Cart";
				}
			}
			throw new ProductNotFoundException("The product you are trying to add is not available");
		}
		throw new UserNotFoundException("Please login or register to add products to cart");
	}

	public String updateCart(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<Product> prodOptional = productRepository.findById((long) productDto.getProductId());
			if (prodOptional.isPresent()) {
				Product product = prodOptional.get();
				Optional<Cart> cartOptional = cartRepository.findByUser(user);
				if (cartOptional.isPresent()) {
					Cart cart = cartOptional.get();
					Optional<CartItems> existingCartItem = cart.getCartItems().stream()
							.filter(item -> item.getProduct().equals(product)).findFirst();
					if (existingCartItem.isEmpty()) {
						throw new ProductNotFoundException(
								"The product you are trying to update is not available in cart");
					}
					CartItems cartItemToUpdate = existingCartItem.get();
					int quantityToRemove = productDto.getProdStock();

					if (quantityToRemove <= cartItemToUpdate.getQuantity()) {
						cart.setCartQuantity(cart.getCartQuantity() - quantityToRemove);
						cart.setCartTotal(cart.getCartTotal() - (product.getProductPrice() * quantityToRemove));

						// update the quantity of product in cart item
						cartItemToUpdate.setQuantity(cartItemToUpdate.getQuantity() - quantityToRemove);
						cartItemToUpdate
								.setTotal(cartItemToUpdate.getTotal() - (quantityToRemove * product.getProductPrice()));

						// If the cart item's quantity is now 0, remove the cart item from the cart
						if (cartItemToUpdate.getQuantity() == 0) {
							cart.getCartItems().remove(cartItemToUpdate);
						}

						cartRepository.save(cart);
						return "Product removed from cart";
					} else {
						throw new ProductNotFoundException("Product to remove exceeds the quantity in cart item");
					}
				}
				throw new CartNotFoundException("No products available in cart");
			}
			throw new ProductNotFoundException("Products not available");
		}
		throw new UserNotFoundException("Please login or register to update the products in cart");
	}

	public String removeProductFromCart(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<Product> prodOptional = productRepository.findById((long) productDto.getProductId());
			if (prodOptional.isPresent()) {
				Product product = prodOptional.get();
				Optional<Cart> cartOptional = cartRepository.findByUser(user);
				if (cartOptional.isPresent()) {
					Cart cart = cartOptional.get();
					Optional<CartItems> existingCartItem = cart.getCartItems().stream()
							.filter(item -> item.getProduct().equals(product)).findFirst();
					if (existingCartItem.isPresent()) {
						CartItems cartItemToDelete = existingCartItem.get();
						cart.setCartQuantity(cart.getCartQuantity() - cartItemToDelete.getQuantity());
						cart.setCartTotal(cart.getCartTotal() - cartItemToDelete.getTotal());
						cart.getCartItems().remove(cartItemToDelete);
						cartRepository.save(cart);
						return "Product Removed Successfully";
					}
					throw new ProductNotFoundException("The product you are trying to remove is not available in cart");
				}
				throw new CartNotFoundException("No products available in cart");
			}
			throw new ProductNotFoundException("Product is not available");
		}
		throw new UserNotFoundException("Please login or register");
	}

	public CartDto viewCart(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<Cart> cartOptional = cartRepository.findByUser(user);
			if (cartOptional.isPresent()) {
				Cart cart = cartOptional.get();

				CartDto cartDto = new CartDto();
				List<CartItemDto> cartItemList = new ArrayList<>();
				for (CartItems cartItem : cart.getCartItems()) {
					CartItemDto cartItemDto = new CartItemDto();

					cartItemDto.setProductName(cartItem.getProduct().getProductName());
					cartItemDto.setProductQuantity(cartItem.getQuantity());
					cartItemDto.setProductTotal(cartItem.getTotal());
					cartItemList.add(cartItemDto);
				}

				cartDto.setCartItems(cartItemList);
				cartDto.setCartQuantity(cart.getCartQuantity());
				cartDto.setCartTotal(cart.getCartTotal());

				return cartDto;
			}
			throw new CartNotFoundException("No products are available in cart to view");
		}
		throw new UserNotFoundException("Please login or register");
	}

	public String cartCheckout(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Optional<Cart> cartOptional = cartRepository.findByUser(user);
			if (cartOptional.isPresent()) {
				Cart cart = cartOptional.get();
				Order order = new Order();
				cart.setOrder(order);
				order.setUser(user);
				order.setOrderDate(LocalDate.now());
				order.setOrderTotal(cart.getCartTotal());
				orderRepository.save(order);

				for (CartItems cartItem : cart.getCartItems()) {

					// to update the order details with respect to order placed
					OrderDetails orderDetails = new OrderDetails();
					orderDetails.setOrder(order);
					orderDetails.setProduct(cartItem.getProduct());
					orderDetails.setQuantity(cartItem.getQuantity());
					orderDetails.setTotal(cartItem.getTotal());
					order.getOrderDetails().add(orderDetails);

					// Reducing the actual stock of the product when order is placed
					Optional<Product> prodOptional = productRepository
							.findByProductNameIgnoreCase(cartItem.getProduct().getProductName());
					if (prodOptional.isPresent()) {
						Product product = prodOptional.get();
						product.setProductStock(product.getProductStock() - cartItem.getQuantity());
						productRepository.save(product);
					}
				}
				return "Order placed successfully" + "\n" + "Your order id is : " + order.getOrederId() + "\n"
						+ "Your Order Will be Delivered By : " + order.getOrderDate().plusDays(5);
			}
			throw new CartNotFoundException("Please add products into cart to checkout");
		}
		throw new UserNotFoundException("Please login or register");
	}

	public List<OrderDto> viewOrders(ProductDto productDto) {
		Optional<User> userOptional = userRepository.findByEmailIgnoreCase(productDto.getUserEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<Order> userOrders = user.getOrder();
			if (userOrders == null || userOrders.isEmpty()) {
				throw new OrderNotFoundException("User has no orders to show");
			}
			List<OrderDto> orderDtoList = new ArrayList<>();
			for (Order orders : user.getOrder()) {
				List<OrderDetailsDto> orderList = new ArrayList<>();
				for (OrderDetails orderDetails : orders.getOrderDetails()) {
					OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
					orderDetailsDto.setName(orderDetails.getProduct().getProductName());
					orderDetailsDto.setQuantity(orderDetails.getQuantity());
					orderDetailsDto.setTotal(orderDetails.getTotal());
					orderList.add(orderDetailsDto);
				}
				if (!orderList.isEmpty()) {
					OrderDto orderDto = new OrderDto();
					orderDto.setOrderDate(orders.getOrderDate());
					orderDto.setOrderTotal(orders.getOrderTotal());
					orderDto.setOrderDetails(orderList);
					orderDtoList.add(orderDto);
				}
			}
			return orderDtoList;
		}
		throw new UserNotFoundException("Please login or register");
	}
}
