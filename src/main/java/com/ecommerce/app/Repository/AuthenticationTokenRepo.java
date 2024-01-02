package com.ecommerce.app.Repository;

import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationTokenRepo extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}

