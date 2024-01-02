package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.CustomException;
import com.ecommerce.app.Repository.CartRepo;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.dto.cart.CartItemDto;
import com.ecommerce.app.dto.cart.addToCartDto;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired ProductService productService;
    @Autowired
    CartRepo cartRepo;
    public void addToCart(addToCartDto addToCartDto, User user) {
    Product product = productService.findById(addToCartDto.getProductId());
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost = totalCost + cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Integer itemId, User user) {
        Optional<Cart> optionalCart = cartRepo.findById(itemId);
        if(optionalCart.isEmpty()) {
            throw new CustomException("Cart Item Id is Invalid");

        }
        Cart cart = optionalCart.get();
        if(cart.getUser() != user) {
            throw new CustomException("Cart item doesn't belong to user");
        }
        cartRepo.delete(cart);
    }
}
