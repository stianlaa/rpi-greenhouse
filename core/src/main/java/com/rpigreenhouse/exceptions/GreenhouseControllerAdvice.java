package com.rpigreenhouse.exceptions;

import com.rpigreenhouse.exceptions.functional.BaseFunctionalException;
import com.rpigreenhouse.exceptions.technical.BaseTechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GreenhouseControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseTechnicalException.class)
    protected ResponseEntity<ExceptionResponse> handleException(BaseTechnicalException ex) {
        return createExceptionResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BaseFunctionalException.class)
    protected ResponseEntity<ExceptionResponse> handleException(BaseFunctionalException ex) {
        return createExceptionResponse(ex.getStatus(), ex.getResponseMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(ExceptionResponse ex) {
        return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown technical error");
    }

    private ResponseEntity<ExceptionResponse> createExceptionResponse(HttpStatus status, String responseMessage) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .cause(responseMessage)
                        .time(LocalDateTime.now())
                        .build(),
                status);
    }
}
