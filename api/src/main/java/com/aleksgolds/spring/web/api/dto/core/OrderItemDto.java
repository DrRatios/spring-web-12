package com.aleksgolds.spring.web.api.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Модель предмета заказа")
public class OrderItemDto {
    @Schema(description = "ID продукта",required = true,example = "1")
    private Long productId;
    @Schema(description = "Название продукта",required = true,maxLength = 255,minLength = 3, example = "Пивко")
    private String productTitle;
    @Schema(description = "Количество продукта",required = true,example = "1")
    private int quantity;
    @Schema(description = "Цена продукта за шт.",required = true,example = "100.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Цена продукта",required = true,example = "100.00")
    private BigDecimal price;

    public OrderItemDto(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productTitle = productDto.getTitle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }


    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
