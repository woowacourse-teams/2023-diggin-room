package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.FeedbackModel
import com.digginroom.digginroom.model.feedback.Feedback

object FeedbackMapper {
    fun Feedback.toModel() = FeedbackModel(
        content
    )

    fun FeedbackModel.toDomain() = Feedback(
        content
    )
}
