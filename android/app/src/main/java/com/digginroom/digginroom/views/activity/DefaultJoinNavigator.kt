package com.digginroom.digginroom.views.activity

import android.content.Context

class DefaultJoinNavigator(
    private val context: Context
) : JoinNavigator {

    override fun navigateToLoginView() {
        (context as JoinActivity).finish()
        LoginActivity.start(context)
    }
}
