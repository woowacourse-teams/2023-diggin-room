package com.digginroom.digginroom.feature.login.navigator

import android.content.Context
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.login.LoginActivity

class DefaultLoginNavigator(
    private val context: Context
) : LoginNavigator {

    override fun navigateToJoinView() {
        JoinActivity.start(context)
        (context as? LoginActivity)?.finish()
    }
}
