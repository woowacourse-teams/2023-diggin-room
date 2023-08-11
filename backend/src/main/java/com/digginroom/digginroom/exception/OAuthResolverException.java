package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public class OAuthResolverException extends DigginRoomException {

    public OAuthResolverException() {
        super("OAuth username을 읽지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
