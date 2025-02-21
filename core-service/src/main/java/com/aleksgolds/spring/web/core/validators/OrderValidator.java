package com.aleksgolds.spring.web.core.validators;

import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.core.entities.OrderItem;
import com.aleksgolds.spring.web.api.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OrderValidator {
//    public void validate(OrderDto orderDto) {
//        List<String> errors = new ArrayList<>();
//        if (orderDto.getPhone() != null && orderDto.getPhone().isBlank()) {
//            errors.add("Контактный телефон должен быть указан");
//        }
//        if (orderDto.getAddress() != null && orderDto.getAddress().isBlank()) {
//            errors.add("Адрес доставки должен быть указан");
//        }
//        if (!errors.isEmpty()) {
//            throw new ValidationException(errors);
//        }
//    }
    public void validate(OrderDetailDto orderDetailDto) {
        List<String> errors = new ArrayList<>();
        if (orderDetailDto.getPhone() != null && orderDetailDto.getPhone().isBlank()) {
            errors.add("Контактный телефон должен быть указан");
        }
        if (orderDetailDto.getAddress() != null && orderDetailDto.getAddress().isBlank()) {
            errors.add("Адрес доставки должен быть указан");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateOrderItems(List<OrderItem> orderItem) {
        List<String> errors = new ArrayList<>();
        if (orderItem.isEmpty()) {
            errors.add("Список продуктов для покупки не может быть пустым");
        }
        if (orderItem.stream().map(p -> p.getQuantity()).anyMatch(q -> q < 1)) {
            errors.add("Количество товаров не может быть меньше 1");
        }
        if (orderItem.stream().map(p -> p.getPricePerProduct()).anyMatch(q -> q.intValue() < 1)) {
            errors.add("Стоимость товара не может быть меньше 1");
        }
        if (orderItem.stream().map(p -> p.getPrice()).anyMatch(q -> q.intValue() < 1)) {
            errors.add("Общая стоимость товаров не может быть меньше 1");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
