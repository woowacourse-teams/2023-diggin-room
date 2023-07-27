package com.digginroom.digginroom.views.activity

import android.content.Context

class JoinNavigator(
    private val context: Context
) : JoinNavigationHandler {

    override fun navigateToLoginView() {
        LoginActivity.start(context)
        (context as? JoinActivity)?.finish()
    }
}
