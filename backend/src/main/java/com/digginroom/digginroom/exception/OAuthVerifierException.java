package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class OAuthVerifierException extends DigginRoomException {

    public OAuthVerifierException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class IdTokenNotReadableException extends OAuthVerifierException {

        public IdTokenNotReadableException() {
            super("OAuth username을 읽지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class InvalidIdTokenException extends OAuthVerifierException {

        public InvalidIdTokenException() {
            super("잘못된 토큰입니다", HttpStatus.BAD_REQUEST);
        }
    }

    public static class ExpireIdTokenException extends OAuthVerifierException {

        public ExpireIdTokenException() {
            super("만료된 토큰입니다", HttpStatus.BAD_REQUEST);
        }
    }

    public static class InvalidJwkUrlException extends OAuthVerifierException {

        public InvalidJwkUrlException() {
            super("잘못된 Url 형식 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
