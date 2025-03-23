package com.aleksgolds.spring.web.cart.models;

import com.aleksgolds.spring.web.api.dto.cart.CartItemDto;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "Модель корзины")
public class Cart {
    @Schema(description = "Лист предметов корзины", required = true)
    private List<CartItem> items;
    @Schema(description = "Цена корзины", required = true, example = "100.00")
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public boolean add(Long id) {
        for (CartItem item : items) {
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
        items.add(new CartItem(productDto));
        recalculate();
    }

    public void remove(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculate();
    }

    public void decrement(Long productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProductId().equals(productId)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem o : items) {
            totalPrice = totalPrice.add(o.getPrice());
        }
    }

    public void merge(Cart another) {
        for (CartItem anotherItem : another.items) {
            boolean merged = false;
            for (CartItem myItem : items) {
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
