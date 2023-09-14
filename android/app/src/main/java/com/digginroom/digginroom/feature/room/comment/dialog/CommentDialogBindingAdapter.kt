package com.digginroom.digginroom.feature.room.comment.dialog

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.state.CommentState

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: CommentState) {
        when (comments) {
            is CommentState.Failed -> Unit
            CommentState.Loading -> Unit
            is CommentState.Succeed -> {
                (recyclerView.adapter as? CommentAdapter)?.submitList(
                    comments.comments
                )
            }
        }
    }
}
