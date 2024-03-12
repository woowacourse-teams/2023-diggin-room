package com.digginroom.digginroom.repository.dto;

import java.time.LocalDateTime;

public record CommentInfo(Long id, String comment, LocalDateTime createdAt, LocalDateTime updatedAt, Long memberId) {
}
