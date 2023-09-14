package com.digginroom.digginroom.feature.room.comment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.CommentModel

class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean =
        oldItem.comment == newItem.comment
}
