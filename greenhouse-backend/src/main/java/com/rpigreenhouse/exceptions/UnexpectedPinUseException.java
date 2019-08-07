package com.rpigreenhouse.exceptions;

public class UnexpectedPinUseException extends GreenhouseFunctionalException {

    public UnexpectedPinUseException(Integer address) {
        super(String.format("Use of a non-functional pin was used, attempted address was %d", address));
    }
}
