package com.rpigreenhouse.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends GreenhouseFunctionalException{
    public InvalidRequestException(String message) {
        super("The request was found to be invalid");
    }
}
