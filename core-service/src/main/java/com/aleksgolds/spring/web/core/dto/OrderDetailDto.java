package com.aleksgolds.spring.web.core.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель деталей заказа")
public class OrderDetailDto {
    @Schema(description = "Адрес заказа", required = true, maxLength = 255, minLength = 3, example = "Улица Пушкина, д. Колотушкино")
    private String address;
    @Schema(description = "Телефон заказчика", required = true, example = "8-800-555-555")
    private String phone;

}
