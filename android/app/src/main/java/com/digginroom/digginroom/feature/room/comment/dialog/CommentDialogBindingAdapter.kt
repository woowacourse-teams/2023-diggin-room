package com.digginroom.digginroom.feature.room.comment.dialog

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState

@BindingAdapter("app:comments")
fun RecyclerView.comments(state: CommentResponseUiState?) {
    state ?: return
    when (state) {
        is CommentResponseUiState.Failed -> Unit
        CommentResponseUiState.Loading -> Unit
        is CommentResponseUiState.Succeed -> {
            (adapter as? CommentAdapter)?.submitList(
                state.comments
            )
        }
    }
}

@BindingAdapter("app:visible")
fun RecyclerView.visible(state: CommentResponseUiState?) {
    state ?: return
    when (state) {
        is CommentResponseUiState.Failed -> Unit
        CommentResponseUiState.Loading -> Unit
        is CommentResponseUiState.Succeed -> {
            visibility = if (state.comments.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}

@BindingAdapter("app:visible")
fun TextView.visible(state: CommentResponseUiState?) {
    state ?: return
    when (state) {
        is CommentResponseUiState.Failed -> Unit
        CommentResponseUiState.Loading -> Unit
        is CommentResponseUiState.Succeed -> {
            visibility = if (state.comments.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
