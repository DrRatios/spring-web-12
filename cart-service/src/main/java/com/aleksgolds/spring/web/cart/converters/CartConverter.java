package com.aleksgolds.spring.web.cart.converters;

import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.dto.cart.CartDto;
import com.aleksgolds.spring.web.api.dto.cart.CartItemDto;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.cart.models.Cart;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class CartConverter {

    public CartDto modelToDto(Cart cart){
        return new CartDto(cart.getItems().stream()
                .map(o->CartItemDto.builder()
                        .price(o.getPrice())
                        .quantity(o.getQuantity())
                        .productTitle(o.getProductTitle())
                        .pricePerProduct(o.getPricePerProduct())
                        .productId(o.getProductId())
                        .build()).collect(Collectors.toList()), cart.getTotalPrice());

    }
    public ProductAnalyticsDto productDtoToProductAnalyticsDto(ProductDto productDto){
        return ProductAnalyticsDto.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .quantity(1)
                .addToCartDate(LocalDateTime.now())
                .build();
    }
}
