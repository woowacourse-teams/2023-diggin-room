package com.digginroom.digginroom.feature.room.bindingadapter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.customview.PostCommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.digginroom.digginroom.model.CommentModel

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: List<CommentModel>) {
        (recyclerView.adapter as? CommentAdapter)?.submitList(comments.toList())
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:postCommentState", "app:postCommentResultListener"],
        requireAll = true
    )
    fun postCommentResultListener(
        editText: EditText,
        postCommentState: PostCommentState,
        postCommentResultListener: ResultListener
    ) {
        when (postCommentState) {
            PostCommentState.SUCCEED -> {
                editText.text.clear()
                postCommentResultListener.onSucceed()
            }

            PostCommentState.FAILED -> {
                editText.text.clear()
                postCommentResultListener.onFailed()
            }

            PostCommentState.READY -> {}
        }
    }
}
