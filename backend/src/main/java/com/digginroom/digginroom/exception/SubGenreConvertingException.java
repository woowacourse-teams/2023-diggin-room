package com.digginroom.digginroom.exception;

import org.springframework.http.HttpStatus;

public class SubGenreConvertingException extends DigginRoomException {

    public SubGenreConvertingException() {
        super("서브장르 변환에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
