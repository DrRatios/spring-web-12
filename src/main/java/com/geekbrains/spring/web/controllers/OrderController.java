package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConventer;
import com.geekbrains.spring.web.dto.OrderDetailDto;
import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.dto.ProfileDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import com.geekbrains.spring.web.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderValidator orderValidator;
    private final UserService userService;
    private final OrderConventer orderConventer;


    @GetMapping()
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findAllOrdersByUsername(principal.getName())
                .stream()
                .map(orderConventer::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody OrderDetailDto orderDetailDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderValidator.validate(orderDetailDto);
        orderService.createOrder(user, orderDetailDto);
    }


}
