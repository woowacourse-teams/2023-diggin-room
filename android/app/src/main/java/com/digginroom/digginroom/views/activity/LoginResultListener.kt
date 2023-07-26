package com.digginroom.digginroom.views.activity

import android.content.Context
import android.widget.EditText

class LoginResultListener(
    private val context: Context,
    private val inputTexts: List<EditText>
) : ResultListener {

    override fun onSucceed() {
        RoomActivity.start(context)
    }

    override fun onFailed() {
        inputTexts.forEach {
            it.text.clear()
        }
    }
}
