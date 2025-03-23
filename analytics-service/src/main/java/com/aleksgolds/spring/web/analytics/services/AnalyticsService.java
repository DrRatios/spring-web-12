package com.aleksgolds.spring.web.analytics.services;

import com.aleksgolds.spring.web.analytics.integrations.CartServiceIntegration;
import com.aleksgolds.spring.web.analytics.integrations.OrderServiceIntegration;
import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderServiceIntegration orderServiceIntegration;
    private final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
    private final List<ProductAnalyticsDto> cartAnalyticsList = new ArrayList<>();
    private final List<ProductAnalyticsDto> orderAnalyticsList = new ArrayList<>();


    public List<ProductAnalyticsDto> getBestSellersPerMonth() {
        return orderAnalyticsList;
    }

    public void updateOrderAnalyticsList() {
        orderServiceIntegration.getAllOrderItems().stream()
                .filter(p -> p.getOrderDate().getMonth().equals(LocalDateTime.now().getMonth()))
                .collect(Collectors.toMap(p -> p, p -> p.getQuantity(), Integer::sum))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEach(e -> {
                    e.getKey().setQuantity(e.getValue());
                    orderAnalyticsList.add(e.getKey());
                });
    }

    public List<ProductAnalyticsDto> getMostViewedProductsPerDay() {
        return cartAnalyticsList;
    }

    public void updateCartAnalyticsList() {
        cartServiceIntegration.getAllCartItems().stream()
                .filter(p -> p.getAddToCartDate().getDayOfWeek().equals(LocalDateTime.now().getDayOfWeek()))
                .collect(Collectors.toMap(p -> p, ProductAnalyticsDto::getQuantity, Integer::sum))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEach(e -> {
                    e.getKey().setQuantity(e.getValue());
                    if (cartAnalyticsList.contains(e.getKey())) {
                        cartAnalyticsList.get(cartAnalyticsList.indexOf(e.getKey()))
                                .setQuantity(cartAnalyticsList.get(cartAnalyticsList.indexOf(e.getKey())).getQuantity() + e.getValue());
                    } else {
                        cartAnalyticsList.add(e.getKey());
                    }
                });
    }
}
