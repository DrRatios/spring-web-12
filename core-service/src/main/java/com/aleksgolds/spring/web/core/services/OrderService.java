package com.aleksgolds.spring.web.core.services;

import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.core.converters.OrderItemConverter;

import com.aleksgolds.spring.web.core.dto.Cart;
import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.entities.Order;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import com.aleksgolds.spring.web.core.repositories.OrderRepository;
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
    private final CartService cartService;
    private final OrderItemConverter orderItemConverter;
    private final ProductsService productsService;

    public List<Order> findAllOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public void createOrder(String username, OrderDetailDto orderDetailDto) {
        String cartKey = cartService.getCartUuidFromSuffix(username);
        Cart cart = cartService.getCurrentCart(cartKey);
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
        cartService.clearCart(cartKey);
    }
}
