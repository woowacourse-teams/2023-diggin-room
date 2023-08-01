package com.digginroom.digginroom.feature.join

import android.content.Context
import android.widget.EditText
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.login.LoginActivity

class JoinResultListener(
    private val context: Context,
    private val inputTexts: List<EditText>,
) : ResultListener {

    override fun onSucceed() {
        LoginActivity.start(context)
        (context as? JoinActivity)?.finish()
    }

    override fun onFailed() {
        inputTexts.forEach {
            it.text.clear()
        }
    }
}
