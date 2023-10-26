package com.digginroom.digginroom.feature.room.comment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.model.CommentItem
import java.lang.IllegalArgumentException

class CommentAdapter(private val menuClickListener: (CommentItem.CommentModel) -> Unit) :
    ListAdapter<CommentItem, ViewHolder>(CommentDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommentItem.Loading -> CommentViewType.LOADING
            is CommentItem.CommentModel -> CommentViewType.COMMENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            CommentViewType.LOADING -> CommentLoadingViewHolder.of(parent)
            CommentViewType.COMMENT -> CommentViewHolder.of(parent) { menuClickListener(getItem(it) as CommentItem.CommentModel) }
            else -> throw IllegalArgumentException("알 수 없는 ViewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CommentViewHolder) holder.bind(getItem(position) as CommentItem.CommentModel)
    }
}
