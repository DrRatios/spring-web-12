package com.aleksgolds.spring.web.cart.models;

import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Модель предмета корзины")
public class CartItem {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;
    @Schema(description = "Название продукта", maxLength = 255,minLength = 3, required = true, example = "1")
    private String productTitle;
    @Schema(description = "Количество продукта", required = true, example = "1")
    private int quantity;
    @Schema(description = "Цена продукта в корзине за шт.", required = true, example = "100.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Цена корзины", required = true, example = "100.00")
    private BigDecimal price;

    public CartItem(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productTitle = productDto.getTitle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }


    public void changeQuantity(int quantity) {
        this.quantity += quantity;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
