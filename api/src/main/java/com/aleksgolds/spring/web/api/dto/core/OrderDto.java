package com.aleksgolds.spring.web.api.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Модель заказа")
public class OrderDto {
    @Schema(description = "ID заказа", required = true,example = "1")
    private Long id;
    @Schema(description = "Имя пользователя",required = true,example = "Вова")
    private String username;
    @Schema(description = "Общая цена",required = true,example = "100.00")
    private BigDecimal totalPrice;
    @Schema(description = "Адрес заказа", required = true,example = "Ул. Пушкина, д. Колотушкино")
    private String address;
    @Schema(description = "Телефон заказчинка", required = true,example = "8-800-555-555")
    private String phone;
    @Schema(description = "Лист предметов заказа", required = true)
    private List<OrderItemDto> items;
}
