package com.digginroom.digginroom.views.activity

import android.content.Context

class DefaultJoinNavigator(
    private val context: Context
) : JoinNavigator {

    override fun navigateToLoginView() {
        LoginActivity.start(context)
        (context as? JoinActivity)?.finish()
    }
}
