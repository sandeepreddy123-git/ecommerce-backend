package com.ecommerce.app.controller;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.Wishlist;
import com.ecommerce.app.service.AuthenticationService;
import com.ecommerce.app.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;
    @Autowired
    AuthenticationService authenticationService;

    // add products to wishlist
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.Authenticate(token);
        User user = authenticationService.getUser(token);
        Wishlist wishlist = new Wishlist(user, product);
        wishlistService.addToWishList(wishlist);
        return new ResponseEntity<>(new ApiResponse(true,"Successfully added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token) {
        User user = authenticationService.getUser(token);
        List<ProductDto> wishlistForUser= wishlistService.getWishlistForUser(user);
        return new ResponseEntity<>(wishlistForUser,HttpStatus.OK);
    }
}
