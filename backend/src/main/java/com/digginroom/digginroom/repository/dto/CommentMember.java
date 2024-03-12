package com.digginroom.digginroom.repository.dto;

import java.time.LocalDateTime;

public record CommentMember(
        Long id,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long memberId,
        String nickname
) {
}
