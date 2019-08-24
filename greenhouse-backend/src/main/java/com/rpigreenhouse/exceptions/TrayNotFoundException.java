package com.rpigreenhouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TrayNotFoundException extends GreenhouseFunctionalException {
    public TrayNotFoundException() {
        super("The specified tray could not be found");
    }
}
