package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public class EncryptException extends DigginRoomException {

    public EncryptException() {
        super("암호화에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
