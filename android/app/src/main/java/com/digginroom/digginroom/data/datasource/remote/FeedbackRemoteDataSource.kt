package com.digginroom.digginroom.data.datasource.remote

import androidx.annotation.Keep
import com.digginroom.digginroom.data.di.Token
import com.digginroom.digginroom.data.entity.FeedbackRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.service.FeedbackService
import retrofit2.Response

class FeedbackRemoteDataSource @Keep constructor(
    @Token private val feedbackService: FeedbackService
) {
    suspend fun postFeedback(feedback: FeedbackRequest) {
        val response: Response<Void> = feedbackService.postFeedback(feedback)

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 200) throw HttpError.Unknown(response)
    }
}
