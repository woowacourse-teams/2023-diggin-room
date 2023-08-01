package com.digginroom.digginroom.feature.login

import android.view.View
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.ResultListener

object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["loginStateListener", "loginState"],
        requireAll = false
    )
    fun onClickForLoginState(
        view: View,
        listener: ResultListener,
        loginState: LoginState
    ) {
        when (loginState) {
            LoginState.SUCCEED -> listener.onSucceed()
            LoginState.FAILED -> listener.onFailed()
            else -> {}
        }
    }
}
