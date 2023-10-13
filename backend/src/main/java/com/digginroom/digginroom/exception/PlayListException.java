package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public class PlayListException extends DigginRoomException {

    public PlayListException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class PlaylistCreateException extends PlayListException {
        public PlaylistCreateException() {
            super("플레이 리스트를 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
