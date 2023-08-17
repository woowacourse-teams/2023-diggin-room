package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.model.CommentModel

class DeleteCommentAlertDialog : DialogFragment() {
    private lateinit var commentMenuDialog: CommentMenuDialog
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var comment: CommentModel
    private var roomId: Long = 0
    private var selectedPosition: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(ALERT_DIALOG_MESSAGE)
                .setPositiveButton(ALERT_DIALOG_POSITIVE_MESSAGE) { _, id ->
                    commentViewModel.deleteComment(
                        roomId,
                        comment.id,
                        selectedPosition
                    )
                    if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                }.setNegativeButton(ALERT_DIALOG_NEGATIVE_MESSAGE) { _, id ->
                    if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun updateCommentMenuDialog(commentMenuDialog: CommentMenuDialog) {
        this.commentMenuDialog = commentMenuDialog
    }

    fun updateCommentViewModel(commentViewModel: CommentViewModel) {
        this.commentViewModel = commentViewModel
    }

    fun updateComment(comment: CommentModel) {
        this.comment = comment
    }

    fun updateRoomId(roomId: Long) {
        this.roomId = roomId
    }

    fun updateSelectedPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
    }

    companion object {
        private const val ALERT_DIALOG_MESSAGE = "정말 삭제하사겠습니까?"
        private const val ALERT_DIALOG_POSITIVE_MESSAGE = "삭제"
        private const val ALERT_DIALOG_NEGATIVE_MESSAGE = "취소"
    }
}
