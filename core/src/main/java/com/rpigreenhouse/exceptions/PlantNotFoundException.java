package com.rpigreenhouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlantNotFoundException extends GreenhouseFunctionalException {
    public PlantNotFoundException() {
        super("The specified plant could not be found");
    }
}
