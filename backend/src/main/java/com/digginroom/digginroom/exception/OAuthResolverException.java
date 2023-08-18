package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class OAuthResolverException extends DigginRoomException {

    public OAuthResolverException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class IdTokenNotReadableException extends OAuthResolverException {

        public IdTokenNotReadableException() {
            super("OAuth username을 읽지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class InvalidIdTokenException extends OAuthResolverException {

        public InvalidIdTokenException() {
            super("잘못된 토큰입니다", HttpStatus.BAD_REQUEST);
        }
    }
}
