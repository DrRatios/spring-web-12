package com.aleksgolds.spring.web.core.converters;


import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.dto.core.OrderDto;
import com.aleksgolds.spring.web.api.dto.cart.CartItemDto;
import com.aleksgolds.spring.web.core.entities.Order;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConventer {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .username(order.getUsername())
                .phone(order.getPhone())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .items(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()))
                .build();
    }

    public OrderItem dtoToEntity(CartItemDto cartItemDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ProductAnalyticsDto entityToAnalyticsDto(OrderItem orderItem) {
        return ProductAnalyticsDto.builder()
                .id(orderItem.getProduct().getId())
                .title(orderItem.getProduct().getTitle())
                .orderDate(orderItem.getCreatedAt())
                .quantity(orderItem.getQuantity())
//                .addToCartDate("")
                .price(orderItem.getProduct().getPrice())
                .build();
    }

}
