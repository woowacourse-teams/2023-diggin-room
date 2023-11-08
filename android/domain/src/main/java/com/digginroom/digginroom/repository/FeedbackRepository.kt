package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.feedback.Feedback

interface FeedbackRepository {

    suspend fun postFeedback(feedback: Feedback): LogResult<Unit>
}
