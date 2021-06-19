package com.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bookstore.dto.CartServiceDTO;

import lombok.Data;

@Entity
@Table(name = "cart_service")
public @Data class CartServiceData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cart_id")
	private int cartId;
	
	@Column
	private int userId;
	private int bookId;
	private long quantity;
	
	public void updateCart(CartServiceDTO cartDTO) {
		this.quantity = cartDTO.quantity;
	}
}
