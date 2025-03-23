package com.aleksgolds.spring.web.api.dto.analytics;

import com.aleksgolds.spring.web.api.dto.core.OrderItemDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Модель продукта для аналитики")
public class ProductAnalyticsDto{
    @Schema(description = "ID продукта",required = true,example = "1")
    private Long id;
    @Schema(description = "Название продукта",required = true,example = "Пивко")
    private String title;
    @Schema(description = "Общая цена",required = true,example = "100.00")
    private BigDecimal price;
    @Schema(description = "Количество продукта",required = true,example = "1")
    private int quantity;
    @Schema(description = "Время покупки товара",example = "LocalDateTime.class")
    private LocalDateTime orderDate;
    @Schema(description = "Время добавления в корзину товара",example = "LocalDateTime.class")
    private LocalDateTime addToCartDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductAnalyticsDto that = (ProductAnalyticsDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}