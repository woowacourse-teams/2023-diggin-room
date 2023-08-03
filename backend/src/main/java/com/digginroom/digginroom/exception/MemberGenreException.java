package com.digginroom.digginroom.exception;

import com.digginroom.digginroom.domain.Genre;
import org.springframework.http.HttpStatus;

public class MemberGenreException extends DigginRoomException {

    public MemberGenreException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class NotFoundException extends MemberGenreException {

        public NotFoundException(final Genre genre) {
            super(genre.getName() + " 멤버 장르가 누락되었습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
