package com.digginroom.digginroom.feature.room.customview.roominfoview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowCommentMenuListener
import com.digginroom.digginroom.model.CommentModel

class CommentAdapter : ListAdapter<CommentModel, CommentViewHolder>(CommentDiffUtilCallback()) {
    lateinit var menuClickListener: ShowCommentMenuListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.of(parent, menuClickListener)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
