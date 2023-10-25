package com.digginroom.digginroom.feedback.dto;

import com.digginroom.digginroom.feedback.domain.Feedback;

public record FeedbackResponse(Long writerId, String content) {

    public static FeedbackResponse of(final Feedback feedback) {
        return new FeedbackResponse(
                feedback.getWriterId(),
                feedback.getContent()
        );
    }
}
