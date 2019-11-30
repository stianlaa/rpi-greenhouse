package com.rpigreenhouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends GreenhouseTechnicalException {
    public ValidationException(Object value, Object receivedObject) {
        super(String.format("A reference with type %s was expected to not be null, expected %s", receivedObject.getClass().toString(), value.toString()));
    }
}
