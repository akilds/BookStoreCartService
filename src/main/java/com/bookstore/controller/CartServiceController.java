package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.CartServiceDTO;
import com.bookstore.model.CartServiceData;
import com.bookstore.service.ICartSService;
import com.bookstore.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cartservice")
@Slf4j
public class CartServiceController {

	@Autowired
	private ICartSService cartService;
	
	//Returns all the items present in the cart
	@GetMapping("/getallitems")
	public ResponseEntity<List<?>> getAllItems(@RequestHeader String userToken) {
		log.info("Get All Items From Cart");
		List<CartServiceData> response = cartService.getAllItems(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Returns all order data of an user
	@GetMapping("/getallordersforuser")
	public ResponseEntity<List<?>> getAllOrdersForUser(@RequestHeader String userToken) {
		log.info("Get All Orders For User");
		List<CartServiceData> response = cartService.getAllOrdersForUser(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Adds a new item into the cart
	@PostMapping("/addtocart")
	public ResponseEntity<Response> addToCart(@RequestHeader String userToken,
											  @RequestHeader int bookId,
										  @RequestBody CartServiceDTO cartDTO) {
		log.info("Add To Cart");
		Response response = cartService.addToCart(userToken,bookId,cartDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing item in the cart
	@PutMapping("/updatecart/{cartId}")
	public ResponseEntity<Response> updateCart(@PathVariable int cartId,
											   @RequestBody CartServiceDTO cartDTO,
											   @RequestHeader String userToken) {
		log.info("Update Cart");
		Response response  = cartService.updateCart(cartId, cartDTO,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Removes an item from the cart
	@DeleteMapping("/removefromcart")
	public ResponseEntity<Response> removeFromCart(@RequestParam int cartId,
											   	   @RequestHeader String userToken) {
		log.info("Removed From Cart");
		Response response  = cartService.removeFromCart(cartId,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
