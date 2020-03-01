package com.rpigreenhouse.exceptions.technical;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseTechnicalException extends RuntimeException {

    protected HttpStatus status;
    protected String responseMessage;

    public BaseTechnicalException(String logMessage, Throwable e) {
        super(logMessage, e);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseTechnicalException(String logMessage, HttpStatus status) {
        super(logMessage);
        this.status = status;
    }

    public BaseTechnicalException(String logMessage, HttpStatus status, Throwable e) {
        super(logMessage, e);
        this.status = status;
    }
}
