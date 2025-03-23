package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.core.converters.OrderConventer;
import com.aleksgolds.spring.web.core.dto.OrderDetailDto;
import com.aleksgolds.spring.web.api.dto.core.OrderDto;
import com.aleksgolds.spring.web.core.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrderController {
    private final OrderService orderService;
    private final OrderConventer orderConventer;

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        return orderConventer.entityToDto(orderService.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказа с ID: " + orderId + " не найдено")));
    }

    @Operation(
            summary = "Запрос на получение заказов текущего пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping()
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findAllOrdersByUsername(username)
                .stream()
                .map(orderConventer::entityToDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на получение информации о всех заказанных(купленных) продуктах",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping("/analytics")
    public List<ProductAnalyticsDto> getAllOrderItems() {
        return orderService.findAll().stream()
                .flatMap(order -> order.getItems().stream())
                .map(orderConventer::entityToAnalyticsDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешное создание заказа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailDto orderDetailDto) {
        orderService.createOrder(username, orderDetailDto);
    }


}
