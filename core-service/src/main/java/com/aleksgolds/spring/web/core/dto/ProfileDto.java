package com.aleksgolds.spring.web.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель профиля")
public class ProfileDto {
    @Schema(description = "Ник пользователя", required = true, maxLength = 255, minLength = 3, example = "Гигачад")
    private String username;
}
