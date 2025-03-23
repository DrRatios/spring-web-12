package com.aleksgolds.spring.web.cart.integrations;

import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.api.exceptions.AppError;
import com.aleksgolds.spring.web.api.exceptions.InternalServerError;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.cart.exceptions.CartIsBrokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {

    private final WebClient coreServiceWebClient;

    public ProductDto findProductById(Long productId) {
        return coreServiceWebClient.get()
                .uri("/api/v1/products/" + productId)
                .retrieve()
                .onStatus(
                        HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class)
                                .map(e->new CartIsBrokenException("Product's server is shutdown")))
                .onStatus(
                        HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).map(ResourceNotFoundException::new))
                .bodyToMono(ProductDto.class)
                .block();
    }
}
