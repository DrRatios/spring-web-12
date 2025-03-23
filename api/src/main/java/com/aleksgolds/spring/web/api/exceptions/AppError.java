package com.aleksgolds.spring.web.api.exceptions;

public class AppError {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setStatusCode(String statusCode) {
        this.code = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppError() {
    }

    public AppError(String statusCode, String message) {
        this.code = statusCode;
        this.message = message;
    }
}
