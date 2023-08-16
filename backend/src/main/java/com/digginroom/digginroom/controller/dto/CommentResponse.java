package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Comment;
import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String writer,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static CommentResponse of(final Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getUsername(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
