package com.rpigreenhouse.exceptions.technical;

import org.springframework.http.HttpStatus;

public class WaterManagerBusyException extends BaseTechnicalException {
    private static final String RESPONSE_MESSAGE = "The watering manager is currently busy.";

    public WaterManagerBusyException() {
        super(RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
