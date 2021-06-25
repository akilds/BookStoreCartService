package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookstore.dto.CartServiceDTO;
import com.bookstore.exception.CartServiceException;
import com.bookstore.model.CartServiceData;
import com.bookstore.repository.CartServiceRepository;
import com.bookstore.util.Response;
import com.bookstore.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartSService implements ICartSService{

	@Autowired
	private CartServiceRepository cartRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Returns all items present in cart
	@Override
	public List<CartServiceData> getAllItems(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Get All Items");
			List<CartServiceData> getAllNotes = cartRepository.findAll();
			return getAllNotes;
		}else {
			log.error("User Is Not Present");
			throw new CartServiceException(400, "User Is Not Present");			
		}	
	}

	//Returns all the orders for user
	@Override
	public List<CartServiceData> getAllOrdersForUser(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Get All Orders For User");
			List<CartServiceData> getAllNotes = cartRepository.findAllByUserId(userId);
			return getAllNotes;
		}else {
			log.error("User Is Not Present");
			throw new CartServiceException(400, "User Is Not Present");			
		}	
	}
	
	//Adds a new item to the cart
	@Override
	public Response addToCart(String userToken, int bookId, CartServiceDTO cartDTO) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isUserIdPresent) {
			String uriB = "http://bookstore-controller/book/verifybookid/" + bookId;
			boolean isBookIdPresent = restTemplate.getForObject(uriB, Boolean.class);
			if(isBookIdPresent) {
				log.info("Add To Cart");
				CartServiceData cart = modelmapper.map(cartDTO, CartServiceData.class);
				cart.setUserId(userId);
				cart.setBookId(bookId);
				cartRepository.save(cart);
				return new Response(200, "Added To Cart Successfully", cart.getCartId());
			}else {
				log.error("Book Doesnt Exist");
				throw new CartServiceException(400, "Book Doesnt Exist");
			}
		}else {
			log.error("User Token Is Invalid");
			throw new CartServiceException(400, "User Token Is Invalid");
		}
	}
	
	//Updates an existing item in the cart
	@Override
	public Response updateCart(int cartId, @Valid CartServiceDTO cartDTO, String userToken) {
		Optional<CartServiceData> isPresent = cartRepository.findById(cartId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent && isPresent.isPresent()) {
			log.info("Update Cart ");
			isPresent.get().updateCart(cartDTO);
			cartRepository.save(isPresent.get());
			return new Response(200, "Item Updated Successfully", cartId);
		}else {
			log.error("User/CartId Doesnt Exist");
			throw new CartServiceException(400, "User/CartId Doesnt Exist");
		}
	}

	//Removes an existing item from the cart
	@Override
	public Response removeFromCart(int cartId, String userToken) {
		Optional<CartServiceData> isPresent = cartRepository.findById(cartId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent && isPresent.isPresent()) {
			log.info("Removed From Cart");
			cartRepository.delete(isPresent.get());
			return new Response(200, "Item Removed From Cart Successfully", cartId);
		}else {
			log.error("User/CartId Is Not Present");
			throw new CartServiceException(400, "User/CartId Is Not Present");
		}
	}
}
