package com.digginroom.digginroom.feature.room.bindingadapter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.customview.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowCommentMenuListener
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
        value = ["app:commentState", "app:postCommentResultListener"],
        requireAll = true
    )
    fun postCommentResultListener(
        editText: EditText,
        commentState: CommentState,
        postCommentResultListener: ResultListener
    ) {
        when (commentState) {
            CommentState.Create.Succeed -> {
                editText.text.clear()
                postCommentResultListener.onSucceed()
            }

            CommentState.Create.Failed -> {
                editText.text.clear()
                postCommentResultListener.onFailed()
            }

            else -> {}
        }
    }
}
