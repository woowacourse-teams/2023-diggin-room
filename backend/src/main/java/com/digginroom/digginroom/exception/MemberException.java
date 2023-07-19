package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public abstract class MemberException extends DigginRoomException {

    public MemberException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class DuplicationException extends MemberException {

        public DuplicationException() {
            super("아이디가 중복되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public static class NotFoundException extends MemberException {

        public NotFoundException() {
            super("회원 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
