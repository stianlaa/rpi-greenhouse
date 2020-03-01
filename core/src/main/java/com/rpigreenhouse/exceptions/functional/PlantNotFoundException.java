package com.rpigreenhouse.exceptions.functional;

import org.springframework.http.HttpStatus;

public class PlantNotFoundException extends BaseFunctionalException {
    private static final String RESPONSE_MESSAGE = "The specified plant could not be found";

    public PlantNotFoundException() {
        super(RESPONSE_MESSAGE, HttpStatus.NOT_FOUND);
        this.responseMessage = RESPONSE_MESSAGE;
    }
}
