package com.digginroom.digginroom.feedback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FeedbackRequest(
        @NotBlank(message = "피드백 내용이 비어있습니다")
        @Size(min = 1, max = 1000, message = "피드백은 1000글자까지 작성 가능합니다")
        String content
) {
}
