package com.ecommerce.app.service;

import com.ecommerce.app.Repository.WishlistRepo;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepo wishlistRepo;
    @Autowired
    ProductService productService;

    public void addToWishList(Wishlist wishlist) {
    wishlistRepo.save(wishlist);
    }

    public List<ProductDto> getWishlistForUser(User user) {
        List<Wishlist> wishlists = wishlistRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for(Wishlist wishlist : wishlists) {
            productDtos.add(productService.getProductDto(wishlist.getProduct()));
        }
        return productDtos;
    }
}
