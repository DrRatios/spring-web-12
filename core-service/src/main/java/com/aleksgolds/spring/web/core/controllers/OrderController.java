package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.core.converters.OrderConventer;
import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.dto.OrderDto;
import com.aleksgolds.spring.web.core.services.OrderService;
import com.aleksgolds.spring.web.core.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderValidator orderValidator;
    private final OrderConventer orderConventer;


    @GetMapping()
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findAllOrdersByUsername(username)
                .stream()
                .map(orderConventer::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailDto orderDetailDto) {
        orderService.createOrder(username, orderDetailDto);
    }


}
