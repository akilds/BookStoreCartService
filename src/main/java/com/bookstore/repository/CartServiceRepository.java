package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.model.CartServiceData;

public interface CartServiceRepository extends JpaRepository<CartServiceData, Integer>{

	@Query(value = "select * from order_data where user_id = :userId", nativeQuery = true)
	List<CartServiceData> findAllByUserId(int userId);
}
