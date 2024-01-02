package com.ecommerce.app.Repository;

import com.ecommerce.app.model.User;
import com.ecommerce.app.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);
}
