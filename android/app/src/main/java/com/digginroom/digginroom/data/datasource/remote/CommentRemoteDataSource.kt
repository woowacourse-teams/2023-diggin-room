package com.digginroom.digginroom.data.datasource.remote

import androidx.annotation.Keep
import com.digginroom.digginroom.data.di.Token
import com.digginroom.digginroom.data.entity.CommentRequest
import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.service.CommentService
import retrofit2.Response

class CommentRemoteDataSource @Keep constructor(
    @Token private val commentService: CommentService
) {
    suspend fun findComments(
        roomId: Long,
        lastCommentId: Long? = null,
        size: Int
    ): CommentsResponse {
        val response: Response<CommentsResponse> = if (lastCommentId == null) {
            commentService.findFirstComments(roomId, size)
        } else {
            commentService.findNextComments(roomId, lastCommentId, size)
        }

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }

    suspend fun postComment(roomId: Long, comment: String): CommentResponse {
        val response: Response<CommentResponse> =
            commentService.postComment(roomId, CommentRequest(comment))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 201) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.Unknown(response)
    }

    suspend fun updateComment(roomId: Long, commentId: Long, comment: String): CommentResponse {
        val response: Response<CommentResponse> =
            commentService.updateComment(roomId, commentId, CommentRequest(comment))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.Unknown(response)
    }

    suspend fun deleteComment(roomId: Long, commentId: Long) {
        val response: Response<Void> = commentService.deleteComment(roomId, commentId)

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 200) throw HttpError.Unknown(response)
    }
}
