package com.aleksgolds.spring.web.core.services;

import com.aleksgolds.spring.web.api.dto.CartDto;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.core.converters.OrderItemConverter;


import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.entities.Order;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import com.aleksgolds.spring.web.core.repositories.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemConverter orderItemConverter;
    private final ProductsService productsService;
    @Autowired
    private RestTemplate restTemplate;

    public List<Order> findAllOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public void createOrder(String username, OrderDetailDto orderDetailDto) {
        String cartUrl = "http://localhost:5555/cart/api/v1/cart/";
        CartDto cart = restTemplate.getForObject(cartUrl + username, CartDto.class);
        Order order = Order.builder()
                .address(orderDetailDto.getAddress())
                .phone(orderDetailDto.getPhone())
                .totalPrice(cart.getTotalPrice())
                .username(username)
                .build();

        List<OrderItem> items = cart.getItems().stream().map(
                        orderItemDto -> OrderItem.builder()
                                .order(order)
                                .product(productsService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                                .pricePerProduct(orderItemDto.getPricePerProduct())
                                .price(orderItemDto.getPrice())
                                .quantity(orderItemDto.getQuantity())
                                .build())
                .collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        restTemplate.getForObject(cartUrl + "clear", CartDto.class);
    }
}
