package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.remote.FeedbackRemoteDataSource
import com.digginroom.digginroom.data.entity.mapper.FeedbackMapper.toEntity
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.feedback.Feedback
import com.digginroom.digginroom.repository.FeedbackRepository

class DefaultFeedbackRepository @Keep constructor(
    private val feedbackRemoteDataSource: FeedbackRemoteDataSource
) : FeedbackRepository {

    override suspend fun postFeedback(feedback: Feedback): LogResult<Unit> {
        return logRunCatching {
            feedbackRemoteDataSource.postFeedback(feedback.toEntity())
        }
    }
}
