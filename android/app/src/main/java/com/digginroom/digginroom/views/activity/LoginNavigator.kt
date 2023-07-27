package com.digginroom.digginroom.views.activity

import android.content.Context

class LoginNavigator(
    private val context: Context
) : LoginNavigationHandler {

    override fun navigateToJoinView() {
        JoinActivity.start(context)
        (context as? LoginActivity)?.finish()
    }
}
