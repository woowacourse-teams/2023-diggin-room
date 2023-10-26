package com.digginroom.digginroom.feature.room.comment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.CommentItem
import com.digginroom.digginroom.model.CommentItem.CommentModel

class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentItem>() {
    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return if (newItem is CommentModel && oldItem is CommentModel) {
            oldItem.id == newItem.id
        } else {
            true
        }
    }

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return if (newItem is CommentModel && oldItem is CommentModel) {
            oldItem.comment == newItem.comment
        } else {
            true
        }
    }
}
