package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.core.dto.ProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Профиль", description = "Методы работы с профилем пользователя")
public class ProfileController {
    @Operation(
            summary = "Запрос на получение информации о профиле пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )
    @GetMapping
    public ProfileDto getCurrentUserInfo(@RequestHeader
                                         @Parameter(description = "Ник пользователя", required = true) String username) {
        return new ProfileDto(username);
    }
}
