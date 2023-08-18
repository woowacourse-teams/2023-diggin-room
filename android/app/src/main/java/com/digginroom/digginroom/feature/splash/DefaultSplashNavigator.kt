package com.digginroom.digginroom.feature.splash

import android.content.Context
import com.digginroom.digginroom.feature.genretaste.GenreTasteActivity
import com.digginroom.digginroom.feature.login.LoginActivity
import com.digginroom.digginroom.feature.room.RoomActivity

class DefaultSplashNavigator(private val context: Context) : SplashNavigator {

    override fun navigateToLoginView() {
        LoginActivity.start(context)
        (context as? SplashActivity)?.finish()
    }

    override fun navigateToGenreTasteView() {
        GenreTasteActivity.start(context)
        (context as? SplashActivity)?.finish()
    }

    override fun navigateToRoomView() {
        RoomActivity.start(context)
        (context as? SplashActivity)?.finish()
    }
}
