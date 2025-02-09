package com.geekbrains.spring.web.controllers;


import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductById(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }

    @DeleteMapping("/removeProduct/{id}")
    public void removeProductFromCart(@PathVariable Long id) {
        cartService.getCurrentCart().decreaseProduct(id);
    }
}
