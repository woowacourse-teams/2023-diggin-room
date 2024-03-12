package com.digginroom.digginroom.service.dto;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.repository.dto.CommentInfo;
import com.digginroom.digginroom.repository.dto.CommentMember;
import com.digginroom.digginroom.repository.dto.MemberNickname;
import java.time.LocalDateTime;
import java.util.Objects;

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
                nickname,
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                isOwner
        );
    }

    public static CommentResponse of(final CommentInfo commentInfo, final MemberNickname memberNickname,
                                     final Long memberId) {
        return new CommentResponse(
                commentInfo.id(),
                memberNickname.nickName(),
                commentInfo.comment(),
                commentInfo.createdAt(),
                commentInfo.updatedAt(),
                Objects.equals(commentInfo.memberId(), memberId)
        );
    }
}
