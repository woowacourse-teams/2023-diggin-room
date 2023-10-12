package com.digginroom.digginroom.data.entity.mapper

import com.digginroom.digginroom.data.entity.FeedbackRequest
import com.digginroom.digginroom.model.feedback.Feedback

object FeedbackMapper {

    fun Feedback.toEntity() = FeedbackRequest(
        content
    )
}
