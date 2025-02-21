package com.aleksgolds.spring.web.core.converters;


import com.aleksgolds.spring.web.core.dto.OrderDto;
import com.aleksgolds.spring.web.core.dto.OrderItemDto;
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

    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
