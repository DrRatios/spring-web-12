package com.geekbrains.spring.web.dto;

import com.geekbrains.spring.web.entities.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public boolean addProduct(Long id) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(id)) {
                item.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        if (addProduct(product.getId())) {
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public void remove(Long id) {
        items.removeIf(item -> item.getProductId().equals(id));
        recalculate();
    }

    public void decreaseProduct(Long id) {
        Iterator<OrderItemDto> iterator = items.iterator();
        while(iterator.hasNext()) {
            OrderItemDto item = iterator.next();
            if (item.getProductId().equals(id)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }


        for (OrderItemDto item : items) {
            if (item.getProductId().equals(id)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {

                }
            }
        }
        recalculate();
    }

    public void clear (){
        items.clear();
        totalPrice = 0;
    }

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto item : items) {
            totalPrice += item.getPrice();
        }
    }
}
