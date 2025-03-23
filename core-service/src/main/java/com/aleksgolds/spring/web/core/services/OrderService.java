package com.aleksgolds.spring.web.core.services;

import com.aleksgolds.spring.web.api.dto.cart.CartDto;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.core.converters.OrderItemConverter;


import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.entities.Order;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import com.aleksgolds.spring.web.core.integrations.CartServiceIntegration;
import com.aleksgolds.spring.web.core.repositories.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductsService productsService;
    private final CartServiceIntegration cartServiceIntegration;

    public List<Order> findAllOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public List<Order> findAll() {return orderRepository.findAll();}

    @Transactional
    public void createOrder(String username, OrderDetailDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
