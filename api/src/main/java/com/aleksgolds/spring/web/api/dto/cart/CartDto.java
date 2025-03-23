package com.aleksgolds.spring.web.api.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель корзины")
public class CartDto {
    @Schema(description = "Лист предметов корзины", required = true)
    private List<CartItemDto> items;
    @Schema(description = "Общая цена",required = true,example = "100.00")
    private BigDecimal totalPrice;
}
