package com.digginroom.digginroom.exception;

import com.digginroom.digginroom.domain.room.Genre;
import org.springframework.http.HttpStatus;

public class GenreException extends DigginRoomException {

    public GenreException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class MemberGenreNotFoundException extends GenreException {

        public MemberGenreNotFoundException(final Genre genre) {
            super(genre.getName() + " 멤버 장르가 누락되었습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class GenreNotFoundException extends GenreException {

        public GenreNotFoundException(final String input) {
            super("잘못된 장르(" + input + ")를 입력했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
