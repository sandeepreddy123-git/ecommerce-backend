package com.ecommerce.app.Repository;

import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
