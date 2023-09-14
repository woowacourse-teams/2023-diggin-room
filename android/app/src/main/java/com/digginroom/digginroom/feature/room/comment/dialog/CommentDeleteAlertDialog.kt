package com.digginroom.digginroom.feature.room.comment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.digginroom.digginroom.feature.room.comment.uistate.CommentDeleteAlertUiState

class CommentDeleteAlertDialog(
    private val uiState: CommentDeleteAlertUiState
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(ALERT_DIALOG_MESSAGE)
                .setPositiveButton(ALERT_DIALOG_POSITIVE_MESSAGE) { _, _ ->
                    uiState.deleteComment()
                    uiState.dismissParentDialog()
                }.setNegativeButton(ALERT_DIALOG_NEGATIVE_MESSAGE) { _, _ ->
                    uiState.dismissParentDialog()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        private const val ALERT_DIALOG_MESSAGE = "정말 삭제하사겠습니까?"
        private const val ALERT_DIALOG_POSITIVE_MESSAGE = "삭제"
        private const val ALERT_DIALOG_NEGATIVE_MESSAGE = "취소"
    }
}
