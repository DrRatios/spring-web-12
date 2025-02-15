package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Order;

import com.geekbrains.spring.web.entities.OrderItem;

import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConventer {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
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
