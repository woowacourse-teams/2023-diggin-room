package com.digginroom.digginroom.service.dto;

import com.digginroom.digginroom.domain.comment.Comment;
import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String writer,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isOwner
) {

    public static CommentResponse of(final CommentMember commentMember, final Long memberId) {
        return new CommentResponse(
                commentMember.id(),
                commentMember.nickname(),
                commentMember.comment(),
                commentMember.createdAt(),
                commentMember.updatedAt(),
                Objects.equals(commentMember.memberId(), memberId)
        );
    }

    public static CommentResponse of(final Comment comment, boolean isOwner, String nickname) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getNickname(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                isOwner
        );
    }
}
