package com.digginroom.digginroom.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public abstract class CommentException extends DigginRoomException {

    public CommentException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class NotFoundException extends CommentException {
        public NotFoundException() {
            super("존재하지 않은 댓글입니다.", NOT_FOUND);
        }
    }

    public static class NotOwnerException extends CommentException {
        public NotOwnerException() {
            super("권한이 없습니다.", FORBIDDEN);
        }
    }

    public static class NotSameRoomException extends CommentException {
        public NotSameRoomException() {
            super("해당 룸의 댓글이 아닙니다.", BAD_REQUEST);
        }
    }

    public static class InvalidCommentSizeException extends CommentException {
        public InvalidCommentSizeException() {
            super("1개 이상의 댓글을 조회 해야합니다.", BAD_REQUEST);
        }
    }

    public static class InvalidLastCommentIdException extends CommentException {
        public InvalidLastCommentIdException() {
            super("댓글 ID는 양수여야합니다.", BAD_REQUEST);
        }
    }
}
