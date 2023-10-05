package com.digginroom.digginroom.feedback.dto;

import com.digginroom.digginroom.feedback.domain.Feedback;

public record FeedbackResponse(String content) {

    public static FeedbackResponse of(Feedback feedback) {
        return new FeedbackResponse(feedback.getContent());
    }
}