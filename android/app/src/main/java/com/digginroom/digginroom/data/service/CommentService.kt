package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.CommentRequest
import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {

    @GET("/rooms/{id}/comments")
    suspend fun findComments(
        @Path(value = "id") roomId: Long
    ): Response<CommentsResponse>

    @POST("/rooms/{id}/comments")
    suspend fun postComment(
        @Path(value = "id") roomId: Long,
        @Body commentRequest: CommentRequest
    ): Response<CommentResponse>

    @PATCH("/rooms/{roomId}/comments/{commentId}")
    suspend fun updateComment(
        @Path(value = "roomId") roomId: Long,
        @Path(value = "commentId") commentId: Long,
        @Body commentRequest: CommentRequest
    ): Response<CommentResponse>
}
