package com.digginroom.digginroom.views.activity

import android.content.Context

class DefaultLoginNavigator(
    private val context: Context
) : LoginNavigator {

    override fun navigateToJoinView() {
        JoinActivity.start(context)
        (context as? LoginActivity)?.finish()
    }
}
