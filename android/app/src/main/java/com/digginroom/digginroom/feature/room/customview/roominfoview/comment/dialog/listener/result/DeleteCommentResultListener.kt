package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result

import android.content.Context
import android.widget.Toast
import com.digginroom.digginroom.feature.ResultListener

class DeleteCommentResultListener(val context: Context) : ResultListener {
    override fun onSucceed() {
        Toast.makeText(context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onFailed() {
        Toast.makeText(context, "댓글 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
    }
}
