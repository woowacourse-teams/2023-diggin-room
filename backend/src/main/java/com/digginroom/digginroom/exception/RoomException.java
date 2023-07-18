package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class RoomException extends DigginRoomException {

    public RoomException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class NotFound extends RoomException {

        public NotFound(final Long id) {
            super(id + "에 해당하는 룸이 없습니다", HttpStatus.NOT_FOUND);
        }
    }
}
