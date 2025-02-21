package com.aleksgolds.spring.web.core.converters;

import com.aleksgolds.spring.web.core.dto.OrderItemDto;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getId())
                .productTitle(orderItem.getProduct().getTitle())
                .pricePerProduct(orderItem.getPricePerProduct())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
