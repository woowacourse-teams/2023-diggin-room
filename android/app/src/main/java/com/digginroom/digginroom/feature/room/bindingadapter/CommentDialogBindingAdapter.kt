package com.digginroom.digginroom.feature.room.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roomplayer.CommentState

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, commentState: CommentState) {
        when (commentState) {
            is CommentState.Error -> {
            }

            CommentState.Loading -> {
            }

            is CommentState.Success -> {
                (recyclerView.adapter as? CommentAdapter)?.submitList(commentState.comments)
            }
        }
    }
}
