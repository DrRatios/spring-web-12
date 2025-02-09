package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.converters.OrderItemConverter;
import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.OrderDetailDto;
import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final OrderItemConverter orderItemConverter;
    private final ProductsService productsService;

    public List<Order> findAllOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

//    @Transactional
//    public void createOrder(String username, OrderDto orderDto) {
//        Cart cart = cartService.getCurrentCart();
//        Order order = Order.builder()
//                .address(orderDto.getAddress())
//                .phone(orderDto.getPhone())
//                .totalPrice(cart.getTotalPrice())
//                .user(userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found")))
//                .build();
//        log.info(order.toString());
//        List<OrderItem> items = cart.getItems().stream().map(
//                orderItemDto -> OrderItem.builder()
//                        .order(order)
//                        .product(productsService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
//                        .pricePerProduct(orderItemDto.getPricePerProduct())
//                        .price(orderItemDto.getPrice())
//                        .quantity(orderItemDto.getQuantity())
//                        .build())
//                .collect(Collectors.toList());
//        order.setItems(items);
//        orderRepository.save(order);
//        cartService.clear();
//    }

    @Transactional
    public void createOrder(User user, OrderDetailDto orderDetailDto) {
        String cartKey = cartService.getCartUuidFromSuffix(user.getUsername());
        Cart cart = cartService.getCurrentCart(cartKey);
        Order order = Order.builder()
                .address(orderDetailDto.getAddress())
                .phone(orderDetailDto.getPhone())
                .totalPrice(cart.getTotalPrice())
                .user(user)
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
        cartService.clearCart(cartKey);
    }
}
