package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class DigginRoomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public DigginRoomException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
