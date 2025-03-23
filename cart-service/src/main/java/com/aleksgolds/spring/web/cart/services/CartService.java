package com.aleksgolds.spring.web.cart.services;


import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.cart.converters.CartConverter;
import com.aleksgolds.spring.web.cart.integrations.ProductsServiceIntegration;
import com.aleksgolds.spring.web.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductsServiceIntegration productsServiceIntegration;
    private List<ProductAnalyticsDto> productAnalyticsDtoList = new ArrayList<>();
    private final CartConverter cartConverter;
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    @Value(value = "${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public ProductDto addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsServiceIntegration.findProductById(productId);
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        execute(cartKey, c -> {
            c.add(productDto);
        });
        productAnalyticsDtoList.add(cartConverter.productDtoToProductAnalyticsDto(productDto));
//        log.info(productAnalyticsDtoList.toString());
        return productDto;
    }

    public List<ProductAnalyticsDto> getProductAnalyticsDtoList() {
//        List<Cart> allCarts = redisTemplate.opsForValue().
        List<ProductAnalyticsDto> newList = new ArrayList<>();
        productAnalyticsDtoList.stream().forEach(product -> {
            newList.add(product);
        });
        log.info(newList.toString());
        productAnalyticsDtoList.clear();
        return newList;
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}

