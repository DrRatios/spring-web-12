package com.aleksgolds.spring.web.api.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", required = true,example = "1")
    private Long id;
    @Schema(description = "Модель продукта",required = true, maxLength = 255,minLength = 3,example = "Пивко")
    private String title;
    @Schema(description = "Модель продукта",required = true, example = "100.00")
    private BigDecimal price;
}
