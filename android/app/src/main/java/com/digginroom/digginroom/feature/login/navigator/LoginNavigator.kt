package com.digginroom.digginroom.feature.login.navigator

import android.content.Context
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.login.LoginActivity

class LoginNavigator(
    private val context: Context,
) : LoginNavigationHandler {

    override fun navigateToJoinView() {
        JoinActivity.start(context)
        (context as? LoginActivity)?.finish()
    }
}
