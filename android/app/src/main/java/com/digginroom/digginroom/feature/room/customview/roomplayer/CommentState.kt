package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.model.CommentModel

sealed class CommentState {
    data class Error(val throwable: Throwable) : CommentState()
    object Loading : CommentState()
    data class Success(val roomId: Long, val comments: List<CommentModel>) : CommentState()
}
