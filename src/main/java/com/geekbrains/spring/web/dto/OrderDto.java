package com.geekbrains.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String username;
    private int totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDto> items;


}
