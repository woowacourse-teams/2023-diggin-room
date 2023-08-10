package com.digginroom.digginroom.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "댓글을 입력해주세요.")
        @Max(value = 500, message = "댓글은 500자 이하이어야 합니다.")
        String comment
) {
}
