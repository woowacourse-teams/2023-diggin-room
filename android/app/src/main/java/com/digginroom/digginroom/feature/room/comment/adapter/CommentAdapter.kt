package com.digginroom.digginroom.feature.room.comment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState
import java.lang.IllegalArgumentException

class CommentAdapter(private val menuClickListener: (CommentUiState.CommentModel) -> Unit) :
    ListAdapter<CommentUiState, ViewHolder>(CommentDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return CommentViewType.valueOf(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            CommentViewType.LOADING.ordinal -> CommentLoadingViewHolder.of(parent)
            CommentViewType.COMMENT.ordinal -> CommentViewHolder.of(parent) {
                menuClickListener(
                    getItem(it) as CommentUiState.CommentModel
                )
            }

            else -> throw IllegalArgumentException("알 수 없는 ViewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CommentViewHolder) holder.bind(getItem(position) as CommentUiState.CommentModel)
    }
}
