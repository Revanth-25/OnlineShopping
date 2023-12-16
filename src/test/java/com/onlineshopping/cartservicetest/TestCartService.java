package com.onlineshopping.cartservicetest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.onlineshopping.dto.CartDto;
import com.onlineshopping.dto.OrderDto;
import com.onlineshopping.dto.ProductDto;
import com.onlineshopping.entity.Cart;
import com.onlineshopping.entity.CartItems;
import com.onlineshopping.entity.Category;
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
import com.onlineshopping.serviceimpl.CartService;

@SpringBootTest
class TestCartService {

	@InjectMocks
	CartService cartService;

	@Mock
	ProductRepository productRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	CartRepository cartRepository;

	@Mock
	OrderRepository orderRepository;

	@Test
	void testAddTo() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		Cart existingCart = new Cart(1, 12, 450);

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(existingCart));

		// Execute the service method
		String result = cartService.addToCart(productDto);

		// Verify the result
		Assertions.assertEquals("Product added to Cart", result);
	}

	@Test
	void addToCart_NewCart_Success() {
		// Mock data for a new cart
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

		// Execute the service method
		String result = cartService.addToCart(productDto);

		// Verify the result
		Assertions.assertEquals("Product added to Cart", result);
	}

	@Test
	void addToCart_ProductNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.empty());

		// Execute the service method and expect an exception
		Assertions.assertThrows(ProductNotFoundException.class, () -> cartService.addToCart(productDto));
	}

	@Test
	void addToCart_UserNotFound() {

		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());

		// Execute the service method and expect an exception
		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.addToCart(productDto));
	}

	@Test
	void updateCart() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 1, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		Cart cart = new Cart(1, 12, 450);
		CartItems cartItem = new CartItems(4, 400, cart, product);
		cart.getCartItems().add(cartItem);

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

		// Execute the service method
		String result = cartService.updateCart(productDto);

		// Verify the result
		Assertions.assertEquals("Product removed from cart", result);
	}

	@Test
	void updateCart_ProductNotFoundInCartItem() {
		// Mock data
		ProductDto productDto = new ProductDto();
		productDto.setProductId(101);
		productDto.setProdStock(1);
		productDto.setUserEmail("test@gmail.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		Product product = new Product();
		product.setProductId(101);
		Cart cart = new Cart();
		cart.setUser(user);
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail()))
				.thenReturn(Optional.ofNullable(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId()))
				.thenReturn(Optional.ofNullable(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.ofNullable(cart));
		ProductNotFoundException exception = Assertions.assertThrows(ProductNotFoundException.class,
				() -> cartService.updateCart(productDto));
		Assertions.assertEquals("The product you are trying to update is not available in cart",
				exception.getMessage());
	}

	@Test
	void updateCart_CartNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto();
		productDto.setProductId(101);
		productDto.setProdStock(1);
		productDto.setUserEmail("test@gmail.com");
		User user = new User();
		user.setEmail(productDto.getUserEmail());
		Product product = new Product();
		product.setProductId(101);
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail()))
				.thenReturn(Optional.ofNullable(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId()))
				.thenReturn(Optional.ofNullable(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
		Assertions.assertThrows(CartNotFoundException.class, () -> cartService.updateCart(productDto));
	}

	@Test
	void updateCart_UserNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto();
		productDto.setProductId(101);
		productDto.setProdStock(1);
		productDto.setUserEmail("test@gmail.com");

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());

		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.updateCart(productDto));
	}

	@Test
	void removeProductFromCart() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 1, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		Cart cart = new Cart(1, 12, 450);
		CartItems cartItem = new CartItems(4, 400, cart, product);
		cart.getCartItems().add(cartItem);

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

		// Execute the service method
		String result = cartService.removeProductFromCart(productDto);

		// Verify the result
		Assertions.assertEquals("Product Removed Successfully", result);
	}

	@Test
	void removeProductFromCart_ProductNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 1, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		Cart cart = new Cart(1, 12, 450);
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
		Assertions.assertThrows(ProductNotFoundException.class, () -> cartService.removeProductFromCart(productDto));
	}

	@Test
	void removeProductFromCart_CartNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 1, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product(101, "Product1", 100.0, "Description1", 10, null, null,
				new Category("Category1"));
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById((long) productDto.getProductId())).thenReturn(Optional.of(product));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
		Assertions.assertThrows(CartNotFoundException.class, () -> cartService.removeProductFromCart(productDto));
	}

	@Test
	void removeProductFromCart_UserNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto();
		productDto.setUserEmail("test.com");
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.removeProductFromCart(productDto));
	}

	@Test
	void viewCart() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product();
		Cart cart = new Cart(1, 12, 450);
		List<CartItems> cartItems = new ArrayList<>();
		CartItems cartItem = new CartItems(1, 100, cart, product);
		cartItems.add(cartItem);
		cart.setCartItems(cartItems);

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

		CartDto result = cartService.viewCart(productDto);

		Assertions.assertEquals(cart.getCartQuantity(), result.getCartQuantity());
		Assertions.assertEquals(cart.getCartTotal(), result.getCartTotal());
		Assertions.assertEquals(cartItems.size(), result.getCartItems().size());
	}

	@Test
	void viewCart_CartNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto();
		productDto.setUserEmail("test@gmail.com");

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail()))
				.thenReturn(Optional.of(new User()));
		Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.empty());

		// Execute the service method and expect an exception
		Assertions.assertThrows(CartNotFoundException.class, () -> cartService.viewCart(productDto));
	}

	@Test
	void viewCart_UserNotFound() {
		ProductDto productDto = new ProductDto();
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.viewCart(productDto));
	}

	@Test
	void cartCheckout() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product();
		product.setProductId(101);
		product.setProductName("Product1");

		Cart cart = new Cart(1, 12, 450);
		CartItems cartItem = new CartItems(1, 100, cart, product);
		cart.getCartItems().add(cartItem);

		Order order = new Order();
		order.setOrederId(0);
		LocalDate date = LocalDate.now();
		order.setOrderDate(date);
		order.setOrderTotal(2000);

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
		Mockito.when(productRepository.findByProductNameIgnoreCase(Mockito.anyString()))
				.thenReturn(Optional.of(product));
		Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);

		// Execute the service method
		String result = cartService.cartCheckout(productDto);

		Assertions.assertEquals("Order placed successfully" + "\n" + "Your order id is : " + order.getOrederId() + "\n"
				+ "Your Order Will be Delivered By : " + order.getOrderDate().plusDays(5), result);
	}

	@Test
	void cartCheckout_CartNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		Product product = new Product();
		product.setProductId(101);
		product.setProductName("Product1");

		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
		Mockito.when(productRepository.findByProductNameIgnoreCase(Mockito.anyString()))
				.thenReturn(Optional.of(product));

		Assertions.assertThrows(CartNotFoundException.class, () -> cartService.cartCheckout(productDto));

	}

	@Test
	void cartCheckout_UserNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.cartCheckout(productDto));

	}

	@Test
	void viewOrders() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
	    productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		user.setOrder(new ArrayList<>());
		Product product = new Product();
		product.setProductId(101);
		product.setProductName("Product1");
		Order order = new Order();
		order.setOrederId(0);		
		LocalDate date = LocalDate.now();
		order.setOrderDate(date);
		order.setOrderTotal(2000);
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setId(order.getOrederId());
		orderDetails.setOrder(order);
		orderDetails.setProduct(product);
		orderDetails.setQuantity(4);
		orderDetails.setTotal(4000);
		order.getOrderDetails().add(orderDetails);
		user.getOrder().add(order);
		
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		// Execute the service method
		List<OrderDto> result = cartService.viewOrders(productDto);
		Assertions.assertEquals(1, result.size());
	}
	
	@Test
	void viewOrders_OrdersNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto(101, "Product1", 100.0, "Description1", 10, null, null, "Category1");
	    productDto.setUserEmail("test.com");
		User user = new User();
		user.setEmail("test.com");
		
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.of(user));
		// Execute the service method
		Assertions.assertThrows(OrderNotFoundException.class, () -> cartService.viewOrders(productDto));
	}
	
	@Test
	void viewOrders_UserNotFound() {
		// Mock data
		ProductDto productDto = new ProductDto();		
		// Mock repository responses
		Mockito.when(userRepository.findByEmailIgnoreCase(productDto.getUserEmail())).thenReturn(Optional.empty());
		// Execute the service method
		Assertions.assertThrows(UserNotFoundException.class, () -> cartService.viewOrders(productDto));
	}
}