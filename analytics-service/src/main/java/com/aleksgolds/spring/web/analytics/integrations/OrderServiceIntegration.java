package com.aleksgolds.spring.web.analytics.integrations;

import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceIntegration {

    private final WebClient orderServiceWebClient;

    public List<ProductAnalyticsDto> getAllOrderItems() {
        return orderServiceWebClient.get()
                .uri("/core/api/v1/orders/analytics")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductAnalyticsDto>>() {})
                .block();

    }

}
