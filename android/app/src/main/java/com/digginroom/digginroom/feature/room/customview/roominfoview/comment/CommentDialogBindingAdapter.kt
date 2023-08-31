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
        commentState: CommentActionState,
        postCommentResultListener: ResultListener,
        updateCommentResultListener: ResultListener
    ) {
        when (commentState) {
            CommentActionState.Update -> {
                editText.requestFocus()
                editText.setSelection(editText.text.length)
            }

            else -> {
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
        commentState: CommentActionState,
        comment: String,
        selectedCommentId: Long?,
        selectedPosition: Int?
    ) {
        button.setOnClickListener {
            when (commentState) {
                CommentActionState.Post -> {
                    clickListener.postComment(comment)
                }

                CommentActionState.Update -> {
                    if (selectedCommentId == null || selectedPosition == null) return@setOnClickListener
                    clickListener.updateComment(
                        selectedCommentId,
                        comment,
                        selectedPosition
                    )
                }
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
        commentState: CommentActionState
    ) {
        when (commentState) {

            else -> {}
        }
    }
}
