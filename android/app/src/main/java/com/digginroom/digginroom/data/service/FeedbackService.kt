package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.FeedbackRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackService {

    @POST("/feedbacks")
    suspend fun postFeedback(
        @Body feedback: FeedbackRequest
    ): Response<Void>
}
