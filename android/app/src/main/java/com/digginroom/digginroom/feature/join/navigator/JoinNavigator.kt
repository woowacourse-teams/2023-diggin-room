package com.digginroom.digginroom.feature.join.navigator

import android.content.Context
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.login.LoginActivity

class JoinNavigator(
    private val context: Context
) : JoinNavigationHandler {

    override fun navigateToLoginView() {
        LoginActivity.start(context)
        (context as? JoinActivity)?.finish()
    }
}
