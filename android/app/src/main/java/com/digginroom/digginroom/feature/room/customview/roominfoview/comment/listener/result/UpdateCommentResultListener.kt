package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result

import android.content.Context
import android.widget.Toast
import com.digginroom.digginroom.feature.ResultListener

class UpdateCommentResultListener(val context: Context) : ResultListener {
    override fun onSucceed() {
        Toast.makeText(context, "댓글이 수정되었습니다..", Toast.LENGTH_SHORT).show()
    }

    override fun onFailed() {
        Toast.makeText(context, "댓글 수정에 실패하였습니다.", Toast.LENGTH_SHORT).show()
    }
}
