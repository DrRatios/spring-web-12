package com.aleksgolds.spring.web.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель строки")
public class StringResponse {
    @Schema(description = "Модель строки", required = true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public StringResponse() {
    }
}
