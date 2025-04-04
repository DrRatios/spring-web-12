package com.aleksgolds.spring.web.core;

import com.aleksgolds.spring.web.api.dto.cart.CartDto;
import com.aleksgolds.spring.web.api.dto.cart.CartItemDto;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.entities.Order;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import com.aleksgolds.spring.web.core.entities.ProductEntity;

import com.aleksgolds.spring.web.core.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
//@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersTest {


    private final OrderRepository orderRepository;

//    @Autowired
//    private EntityManager entityManager;

    @Test
    public void createOrderTest() {
        ProductEntity milkEntity = new ProductEntity(1L,"Milk",BigDecimal.valueOf(60.00));
        ProductDto milk = new ProductDto(1L,"Milk", BigDecimal.valueOf(40.00));
        CartItemDto cartItemDtoMilk = new CartItemDto(milk);
        cartItemDtoMilk.changeQuantity(2); //totalPrice = 60*3=180
        CartDto cartDto = new CartDto(List.of(cartItemDtoMilk), cartItemDtoMilk.getPrice());
        OrderDetailDto orderDetailDto = new OrderDetailDto("Murmansk","7-27-67");
        String username = "Ken";
        Order order = Order.builder()
                .address(orderDetailDto.getAddress())
                .phone(orderDetailDto.getPhone())
                .username(username)
                .totalPrice(cartDto.getTotalPrice())
                .build();
        List<OrderItem> items = new ArrayList<>(List.of(OrderItem.builder()
                        .order(order)
                        .product(milkEntity)
                        .quantity(cartItemDtoMilk.getQuantity())
                        .price(cartItemDtoMilk.getPrice())
                        .pricePerProduct(cartItemDtoMilk.getPricePerProduct())
                .build()));
        order.setItems(items);

//        entityManager.persist(order);
//        entityManager.flush();

        orderRepository.save(order);
        List<Order> orders = orderRepository.findAllByUsername(username);
        Assertions.assertEquals(1, orders.size());

//        Assertions.assertEquals(2, orders.size());

        Assertions.assertEquals(3, orders.get(0).getItems().get(0).getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(180), orders.get(0).getItems().get(0).getPrice());


    }

}
