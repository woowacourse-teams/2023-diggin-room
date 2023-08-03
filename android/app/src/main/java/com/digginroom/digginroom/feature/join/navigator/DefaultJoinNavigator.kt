package com.digginroom.digginroom.feature.join.navigator

import android.content.Context
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.login.LoginActivity

class DefaultJoinNavigator(
    private val context: Context
) : JoinNavigator {

    override fun navigateToLoginView() {
        LoginActivity.start(context)
        (context as? JoinActivity)?.finish()
    }
}
