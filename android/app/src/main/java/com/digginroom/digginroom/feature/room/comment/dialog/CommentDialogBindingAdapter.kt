package com.digginroom.digginroom.feature.room.comment.dialog

import android.text.Editable
import android.text.Selection
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState

@BindingAdapter("app:responseState")
fun RecyclerView.comments(responseState: CommentResponseUiState?) {
    responseState ?: return
    when (responseState) {
        is CommentResponseUiState.Failed -> Unit
        CommentResponseUiState.Loading -> Unit
        is CommentResponseUiState.Succeed -> {
            (adapter as? CommentAdapter)?.submitList(
                responseState.comments
            )
            smoothScrollToPosition(0)
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

@BindingAdapter("app:cursorIndex")
fun EditText.cursorIndex(comments: String) {
    val editable: Editable = this.text
    Selection.setSelection(editable, editable.length)
}
