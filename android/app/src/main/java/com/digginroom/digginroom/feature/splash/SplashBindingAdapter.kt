package com.digginroom.digginroom.feature.splash

import android.view.View
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.login.LoginState

object SplashBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["splashNavigator", "loginState"],
        requireAll = false
    )
    fun setOnSplashNavigator(
        view: View,
        navigator: SplashNavigator,
        loginState: LoginState
    ) {
        when (loginState) {
            LoginState.Succeed.Surveyed -> navigator.navigateToRoomView()
            LoginState.Succeed.NotSurveyed -> navigator.navigateToGenreTasteView()
            LoginState.Failed -> navigator.navigateToLoginView()
            else -> {}
        }
    }
}
