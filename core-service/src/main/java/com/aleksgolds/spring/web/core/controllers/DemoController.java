package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.core.integrations.CartServiceIntegration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@Tag(name = "Демо", description = "Методы прямого тестирования связи контроллеров")
public class DemoController {
    private final CartServiceIntegration cartServiceIntegration;

    @Operation(
            summary = "Проверка работы интеграции с CartMS",
            responses = {
                    @ApiResponse(
                            description = "Успешный овтет", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping
    public String demo() {
        cartServiceIntegration.getUserCart("1");
        return "Hello World!";
    }
}
