package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class RecommendException extends DigginRoomException{

    public RecommendException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class UnderBoundWeightException extends RecommendException {

        public UnderBoundWeightException(final int weight) {
            super("가중치 합이 0 이하입니다. 현재 가중치 합:" + weight, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
