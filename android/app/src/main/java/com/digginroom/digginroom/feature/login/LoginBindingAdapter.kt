package com.digginroom.digginroom.feature.login

import android.view.View
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.login.navigator.LoginNavigator

object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["loginStateListener", "loginNavigator", "loginState"],
        requireAll = false
    )
    fun onClickForLoginState(
        view: View,
        listener: ResultListener,
        navigator: LoginNavigator,
        loginState: LoginState
    ) {
        when (loginState) {
            LoginState.Succeed.Surveyed -> listener.onSucceed()
            LoginState.Succeed.NotSurveyed -> navigator.navigateToGenreTasteView()
            LoginState.Failed -> listener.onFailed()
            else -> {}
        }
    }
}
