package com.digginroom.digginroom.feature.room.customview.roominfo.comment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.adapter.CommentAdapter
import com.digginroom.digginroom.model.CommentModel

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: List<CommentModel>?) {
        println(comments)
        (recyclerView.adapter as? CommentAdapter)?.submitList(comments?.toList() ?: listOf())
    }
}
