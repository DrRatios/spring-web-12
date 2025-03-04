package com.aleksgolds.spring.web.cart.controllers;


import com.aleksgolds.spring.web.api.dto.StringResponse;
import com.aleksgolds.spring.web.api.dto.CartDto;
import com.aleksgolds.spring.web.cart.dto.Cart;
import com.aleksgolds.spring.web.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        Cart cart = cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
        return new CartDto(cart.getItems(),cart.getTotalPrice());
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}
