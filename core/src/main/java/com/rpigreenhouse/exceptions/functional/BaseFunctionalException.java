package com.rpigreenhouse.exceptions.functional;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseFunctionalException extends RuntimeException {

    protected HttpStatus status;
    protected String responseMessage;

    public BaseFunctionalException(String logMessage, HttpStatus status) {
        super(logMessage);
        this.status = status;
    }

    public BaseFunctionalException(String logMessage, Throwable e) {
        super(logMessage, e);
    }

    public BaseFunctionalException(String logMessage, HttpStatus status, Throwable e) {
        super(logMessage, e);
        this.status = status;
    }
}
