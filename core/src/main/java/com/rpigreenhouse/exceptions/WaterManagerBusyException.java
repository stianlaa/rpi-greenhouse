package com.rpigreenhouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WaterManagerBusyException extends GreenhouseTechnicalException {
    public WaterManagerBusyException() {
        super("The watering manager is currently busy.");
    }
}
