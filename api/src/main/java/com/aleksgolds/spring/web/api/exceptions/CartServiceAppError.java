package com.aleksgolds.spring.web.api.exceptions;

public class CartServiceAppError extends AppError {
    public enum CartServiceErrors{
        CART_IS_BROKEN, CART_ID_GENERATOR_DISABLED, CART_NOT_FOUND
    }

    public CartServiceAppError(String code, String message) {
        super(code, message);
    }
    public CartServiceAppError() {}
}
