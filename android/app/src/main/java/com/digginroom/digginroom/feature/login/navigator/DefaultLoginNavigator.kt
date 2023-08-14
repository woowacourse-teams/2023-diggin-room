package com.digginroom.digginroom.feature.login.navigator

import android.content.Context
import com.digginroom.digginroom.feature.genretaste.GenreTasteActivity
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.login.LoginActivity

class DefaultLoginNavigator(
    private val context: Context
) : LoginNavigator {

    override fun navigateToJoinView() {
        JoinActivity.start(context)
        (context as? LoginActivity)?.finish()
    }

    override fun navigateToGenreTasteView() {
        GenreTasteActivity.start(context)
        (context as? LoginActivity)?.finish()
    }
}
