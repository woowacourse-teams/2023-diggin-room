package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class RoomException extends DigginRoomException {

    public RoomException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class NotFoundException extends RoomException {

        public NotFoundException(final Long id) {
            super(id + "에 해당하는 룸이 없습니다", HttpStatus.NOT_FOUND);
        }
    }

    public static class EmptyException extends RoomException {

        public EmptyException() {
            super("더 이상 룸이 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class NoMediaSourceException extends RoomException {

        public NoMediaSourceException() {
            super("룸은 미디어소스가 필수입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
