package com.rpigreenhouse.Exceptions;

public class GreenhouseTechnicalException extends RuntimeException {
    public GreenhouseTechnicalException() {
        super();
    }

    public GreenhouseTechnicalException(String message) {
        super(message);
    }
}
