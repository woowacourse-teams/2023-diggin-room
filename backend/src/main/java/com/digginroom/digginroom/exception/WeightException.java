package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class WeightException extends DigginRoomException {

    public WeightException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class WeightTooSmallException extends WeightException {

        public WeightTooSmallException(int weight) {
            super("가중치 " + weight + "는 너무 작습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
