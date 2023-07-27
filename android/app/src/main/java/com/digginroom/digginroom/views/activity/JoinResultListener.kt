package com.digginroom.digginroom.views.activity

import android.content.Context
import android.widget.EditText

class JoinResultListener(
    private val context: Context,
    private val inputTexts: List<EditText>
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
