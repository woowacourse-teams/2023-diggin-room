package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.CommentRequest
import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.service.CommentService
import retrofit2.Response

class CommentRemoteDataSource(private val commentService: CommentService) {
    suspend fun findComments(roomId: Long): CommentsResponse {
        val response: Response<CommentsResponse> = commentService.findComments(roomId)

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }

    suspend fun postComment(roomId: Long, comment: String): CommentResponse {
        val response: Response<CommentResponse> =
            commentService.postComment(roomId, CommentRequest(comment))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 201) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.Unknown(response)
    }

    suspend fun updateComment(roomId: Long, commentId: Long, comment: String): CommentResponse {
        val response: Response<CommentResponse> =
            commentService.updateComment(roomId, commentId, CommentRequest(comment))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.Unknown(response)
    }
}
