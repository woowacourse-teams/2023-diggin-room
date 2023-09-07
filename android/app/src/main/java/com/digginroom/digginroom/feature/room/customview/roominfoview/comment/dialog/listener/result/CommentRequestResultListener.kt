package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result

import android.content.Context
import android.widget.Toast
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentRequestState

class CommentRequestResultListener(
    val context: Context
) : RequestResultListener {
    override fun handleResult(commentRequestState: CommentRequestState?) {
        when (commentRequestState) {
            is CommentRequestState.Succeed -> {
            }

            is CommentRequestState.Failed -> {
                Toast.makeText(
                    context,
                    commentRequestState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
            }
        }
    }
}
