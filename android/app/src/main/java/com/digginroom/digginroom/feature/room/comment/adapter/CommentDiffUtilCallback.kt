package com.digginroom.digginroom.feature.room.comment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState
import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState.CommentModel

class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentUiState>() {
    override fun areItemsTheSame(oldItem: CommentUiState, newItem: CommentUiState): Boolean {
        return newItem is CommentModel && oldItem is CommentModel && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentUiState, newItem: CommentUiState): Boolean {
        return newItem is CommentModel && oldItem is CommentModel && oldItem == newItem
    }
}
