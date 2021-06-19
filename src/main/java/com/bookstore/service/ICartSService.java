package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.CartServiceDTO;
import com.bookstore.model.CartServiceData;
import com.bookstore.util.Response;

public interface ICartSService {

	List<CartServiceData> getAllItems(String userToken);

	Response addToCart(String userToken, int bookId,CartServiceDTO cartDTO);

	Response updateCart(int cartId,CartServiceDTO cartDTO, String userToken);

	Response removeFromCart(int cartId, String userToken);

}
