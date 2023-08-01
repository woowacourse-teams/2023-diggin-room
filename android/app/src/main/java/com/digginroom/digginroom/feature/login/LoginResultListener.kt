package com.digginroom.digginroom.feature.login

import android.content.Context
import android.widget.EditText
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.RoomActivity

class LoginResultListener(
    private val context: Context,
    private val inputTexts: List<EditText>,
) : ResultListener {

    override fun onSucceed() {
        RoomActivity.start(context)
        (context as? LoginActivity)?.finish()
    }

    override fun onFailed() {
        inputTexts.forEach {
            it.text.clear()
        }
    }
}
