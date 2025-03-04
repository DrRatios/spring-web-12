package com.aleksgolds.spring.web.cart.dto;

import com.aleksgolds.spring.web.api.dto.OrderItemDto;
import com.aleksgolds.spring.web.api.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public boolean add(Long id) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(id)) {
                item.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void add(ProductDto productDto) {
        if (add(productDto.getId())) {
            return;
        }
        items.add(new OrderItemDto(productDto));
        recalculate();
    }

    public void remove(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculate();
    }

    public void decrement(Long productId) {
        Iterator<OrderItemDto> iterator = items.iterator();
        while (iterator.hasNext()) {
            OrderItemDto item = iterator.next();
            if (item.getProductId().equals(productId)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }


        for (OrderItemDto item : items) {
            if (item.getProductId().equals(productId)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {

                }
            }
        }
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto item : items) {
            totalPrice += item.getPrice();
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
