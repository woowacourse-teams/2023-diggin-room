package com.digginroom.digginroom.feature.room.customview.roominfoview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.CommentModel

class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean =
        oldItem == newItem
}
