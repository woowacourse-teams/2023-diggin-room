package com.digginroom.digginroom.feature.splash

import android.content.Context
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.login.LoginActivity
import com.digginroom.digginroom.feature.room.RoomActivity

class SplashResultListener(private val context: Context) : ResultListener {

    override fun onSucceed() {
        (context as? SplashActivity)?.finish()
        RoomActivity.start(context)
    }

    override fun onFailed() {
        (context as? SplashActivity)?.finish()
        LoginActivity.start(context)
    }
}
