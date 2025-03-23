package com.aleksgolds.spring.web.analytics.controllers;

import com.aleksgolds.spring.web.analytics.services.AnalyticsService;
import com.aleksgolds.spring.web.api.dto.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class OrderAnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/order")
    public List<ProductAnalyticsDto> getBestSellersPerMonth() {
        return analyticsService.getBestSellersPerMonth();
    }

    @GetMapping("/order/update")
    public void updateOrderAnalyticsList() {
        analyticsService.updateOrderAnalyticsList();
    }

    @GetMapping("/cart")
    public List<ProductAnalyticsDto> getMostViewedProductsPerDay() {
        return analyticsService.getMostViewedProductsPerDay();
    }

    @GetMapping("/cart/update")
    public void updateCartAnalyticsList() {
        analyticsService.updateCartAnalyticsList();
    }
}
