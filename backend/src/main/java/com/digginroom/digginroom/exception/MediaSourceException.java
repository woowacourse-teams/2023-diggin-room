package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class MediaSourceException extends DigginRoomException {

    public MediaSourceException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class NoIdentifierException extends RoomException {

        public NoIdentifierException() {
            super("미디어 소스 식별자는 필수입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
