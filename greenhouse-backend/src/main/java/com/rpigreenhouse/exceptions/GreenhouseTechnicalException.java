package com.rpigreenhouse.exceptions;

public class GreenhouseTechnicalException extends RuntimeException {
    public GreenhouseTechnicalException() {
        super();
    }

    public GreenhouseTechnicalException(String message) {
        super(message);
    }
}
