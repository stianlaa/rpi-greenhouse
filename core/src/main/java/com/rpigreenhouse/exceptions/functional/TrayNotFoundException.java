package com.rpigreenhouse.exceptions.functional;

import org.springframework.http.HttpStatus;

public class TrayNotFoundException extends BaseFunctionalException {
    private static final String RESPONSE_MESSAGE = "The specified tray could not be found";

    public TrayNotFoundException() {
        super(RESPONSE_MESSAGE, HttpStatus.NOT_FOUND);
        this.responseMessage = RESPONSE_MESSAGE;
    }
}
