package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.ShowCommentMenuListener
import com.digginroom.digginroom.feature.room.customview.CommentState
import com.digginroom.digginroom.model.CommentModel

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: List<CommentModel>) {
        (recyclerView.adapter as? CommentAdapter)?.submitList(comments.toList())
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:commentState", "app:postCommentResultListener", "app:updateCommentResultListener"],
        requireAll = false
    )
    fun postCommentResultListener(
        editText: EditText,
        commentState: CommentState,
        postCommentResultListener: ResultListener,
        updateCommentResultListener: ResultListener
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

            CommentState.Edit.Succeed -> {
                editText.text.clear()
                updateCommentResultListener.onSucceed()
            }

            CommentState.Edit.Failed -> {
                editText.text.clear()
                updateCommentResultListener.onFailed()
            }

            is CommentState.Edit -> {
                editText.requestFocus()
                editText.setSelection(editText.text.length)
            }

            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:clickListener", "app:commentState", "app:comment", "app:selectedCommentId", "app:selectedPosition"],
        requireAll = false
    )
    fun clickListener(
        button: AppCompatButton,
        clickListener: CommentEventListener,
        commentState: CommentState,
        comment: String,
        selectedCommentId: Long?,
        selectedPosition: Int?
    ) {
        button.setOnClickListener {
            when (commentState) {
                is CommentState.Create -> clickListener.postComment(comment)
                is CommentState.Edit -> clickListener.updateComment(
                    selectedCommentId!!,
                    comment,
                    selectedPosition!!
                )

                else -> {}
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:onMenuClick")
    fun onMenuClick(recyclerView: RecyclerView, showCommentMenuListener: ShowCommentMenuListener) {
        (recyclerView.adapter as? CommentAdapter)?.menuClickListener = showCommentMenuListener
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:deleteCommentResultListener", "app:commentState"],
        requireAll = true
    )
    fun deleteCommentResultListener(
        constraintLayout: ConstraintLayout,
        deleteCommentResultListener: ResultListener,
        commentState: CommentState
    ) {
        when (commentState) {
            CommentState.Delete.Succeed -> {
                deleteCommentResultListener.onSucceed()
            }

            CommentState.Delete.Failed -> {
                deleteCommentResultListener.onFailed()
            }

            else -> {}
        }
    }
}
