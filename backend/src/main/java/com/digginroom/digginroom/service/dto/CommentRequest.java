package com.digginroom.digginroom.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "댓글을 입력해주세요.")
        @Size(max = 500, message = "댓글은 500자 이하이어야 합니다.")
        String comment
) {
}
