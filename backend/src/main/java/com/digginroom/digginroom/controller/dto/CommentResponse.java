package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Comment;
import java.time.LocalDateTime;

public record CommentResponse(String writer, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static CommentResponse of(final Comment comment) {
        return new CommentResponse(
                comment.getMember().getUsername(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
