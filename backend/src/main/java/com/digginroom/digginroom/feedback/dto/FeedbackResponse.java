package com.digginroom.digginroom.feedback.dto;

import com.digginroom.digginroom.controller.dto.MemberDetailsResponse;
import com.digginroom.digginroom.feedback.domain.Feedback;

public record FeedbackResponse(MemberDetailsResponse writer, String content) {

    public static FeedbackResponse of(final Feedback feedback) {
        return new FeedbackResponse(
                MemberDetailsResponse.of(feedback.getWriter()),
                feedback.getContent()
        );
    }
}
