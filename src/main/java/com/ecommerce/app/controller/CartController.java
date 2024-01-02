package com.ecommerce.app.controller;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.dto.cart.addToCartDto;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.service.AuthenticationService;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.service.ProductService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addtoCart(@RequestBody addToCartDto addToCartDto, @RequestParam("token") String token) {
        authenticationService.Authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.addToCart(addToCartDto, user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        authenticationService.Authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token) {
        authenticationService.Authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been deleted"), HttpStatus.CREATED);
    }
}
