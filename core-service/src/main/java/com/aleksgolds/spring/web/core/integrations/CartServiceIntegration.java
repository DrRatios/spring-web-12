package com.aleksgolds.spring.web.core.integrations;

import com.aleksgolds.spring.web.api.dto.cart.CartDto;
import com.aleksgolds.spring.web.api.exceptions.CartServiceAppError;
import com.aleksgolds.spring.web.api.exceptions.InternalServerError;
import com.aleksgolds.spring.web.core.exceptions.CartServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body->{
                                    if(body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())){
                                        return new CartServiceIntegrationException("Incorrect request: CART_NOT_FOUND");
                                    }
                                    if(body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name())){
                                        return new CartServiceIntegrationException("Incorrect request: CarMS is broken");
                                    }
                                    return new CartServiceIntegrationException("Incorrect request: reason unknown");
                                }
                        ))
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body->{
                                    if(body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_ID_GENERATOR_DISABLED.name())){
                                        return new CartServiceIntegrationException("Incorrect request: CART_ID_GENERATOR_DISABLED");
                                    }
                                    return new CartServiceIntegrationException("Incorrect request: reason unknown");
                                }
                        ))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }

}
