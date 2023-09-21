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

    public static class ExpireIdTokenException extends OAuthResolverException {

        public ExpireIdTokenException() {
            super("만료된 토큰입니다", HttpStatus.BAD_REQUEST);
        }
    }

    public static class InvalidJwkUrlException extends OAuthResolverException {

        public InvalidJwkUrlException() {
            super("잘못된 Url 형식 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class UnsupportedProviderException extends OAuthResolverException {

        public UnsupportedProviderException(final String provider) {
            super("지원하지 않는 프로바이더입니다.: " + provider, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class UnsupportedIdTokenException extends OAuthResolverException {

        public UnsupportedIdTokenException(final String issuer) {
            super("지원하지 않는 Issuer의 IdToken입니다: " + issuer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
