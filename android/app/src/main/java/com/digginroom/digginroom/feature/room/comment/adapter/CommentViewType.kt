package com.digginroom.digginroom.feature.room.comment.adapter

import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState

enum class CommentViewType {
    COMMENT, LOADING;

    companion object {
        fun valueOf(commentItem: CommentUiState): Int {
            return when (commentItem) {
                is CommentUiState.Loading -> LOADING.ordinal
                is CommentUiState.CommentModel -> COMMENT.ordinal
            }
        }
    }
}
