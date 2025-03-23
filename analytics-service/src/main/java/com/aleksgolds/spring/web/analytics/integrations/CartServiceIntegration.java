package com.aleksgolds.spring.web.analytics.integrations;


import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.dto.cart.CartDto;
import com.aleksgolds.spring.web.api.dto.cart.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient orderServiceWebClient;

    public List<ProductAnalyticsDto> getAllCartItems() {
        return orderServiceWebClient.get()
                .uri("/cart/api/v1/cart/analytics")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductAnalyticsDto>>() {})
                .block();

    }
}
