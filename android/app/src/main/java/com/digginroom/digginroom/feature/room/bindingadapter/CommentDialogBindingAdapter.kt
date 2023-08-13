package com.digginroom.digginroom.feature.room.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.digginroom.digginroom.model.CommentModel

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: List<CommentModel>) {
        (recyclerView.adapter as? CommentAdapter)?.submitList(comments.toList())
    }
}
