package com.digginroom.digginroom.feature.room.customview

import android.content.Context
import android.widget.Toast
import com.digginroom.digginroom.feature.ResultListener

class PostCommentResultListener(val context: Context) : ResultListener {
    override fun onSucceed() {
    }

    override fun onFailed() {
        Toast.makeText(context, "댓글 전송에 실패하였습니다.", Toast.LENGTH_SHORT).show()
    }
}
