package com.aleksgolds.spring.web.core.exceptions;

import com.aleksgolds.spring.web.api.exceptions.AppError;
import com.aleksgolds.spring.web.api.exceptions.InternalServerError;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.api.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError("RESOURCE NOT FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchCartServiceIntegrationError(CartServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError("CART SERVICE INTEGRATION ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }
}
