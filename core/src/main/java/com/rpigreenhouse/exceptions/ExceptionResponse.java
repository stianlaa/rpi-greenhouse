package com.rpigreenhouse.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ExceptionResponse {
    private String cause;
    private LocalDateTime time;
}
