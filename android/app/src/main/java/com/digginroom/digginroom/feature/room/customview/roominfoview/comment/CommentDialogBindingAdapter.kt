package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentMenuListener
import com.digginroom.digginroom.model.CommentModel

object CommentDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:comments")
    fun comments(recyclerView: RecyclerView, comments: List<CommentModel>?) {
        (recyclerView.adapter as? CommentAdapter)?.submitList(comments?.toList() ?: listOf())
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
            CommentState.Update.Ready -> {
                editText.requestFocus()
                editText.setSelection(editText.text.length)
            }

            CommentState.Post.Succeed -> {
                editText.text.clear()
                postCommentResultListener.onSucceed()
            }

            CommentState.Post.Failed -> {
                editText.text.clear()
                postCommentResultListener.onFailed()
            }

            CommentState.Update.Succeed -> {
                editText.text.clear()
                updateCommentResultListener.onSucceed()
            }

            CommentState.Update.Failed -> {
                editText.text.clear()
                updateCommentResultListener.onFailed()
            }

            else -> {
                editText.text.clear()
            }
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
                CommentState.Delete.Succeed,
                CommentState.Post.Succeed,
                CommentState.Post.Ready -> {
                    clickListener.postComment(comment)
                }

                CommentState.Delete.Succeed,
                CommentState.Update.Succeed,
                CommentState.Update.Ready -> {
                    if (selectedCommentId == null || selectedPosition == null) return@setOnClickListener
                    clickListener.updateComment(
                        selectedCommentId,
                        comment,
                        selectedPosition
                    )
                }

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
