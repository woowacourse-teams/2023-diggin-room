package com.digginroom.digginroom.feature.login

import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.ResultListener

object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["onClickForLoginState", "listener", "loginState"],
        requireAll = false
    )
    fun onClickForLoginState(
        button: Button,
        onClick: () -> Unit,
        listener: ResultListener,
        loginState: LoginState
    ) {
        button.setOnClickListener {
            onClick()
        }
        when (loginState) {
            LoginState.SUCCEED -> listener.onSucceed()
            LoginState.FAILED -> listener.onFailed()
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleOnLoading")
    fun setVisibleOnLoading(view: View, loginState: LoginState) {
        view.isVisible = loginState == LoginState.LOADING
    }

    @JvmStatic
    @BindingAdapter("app:visibleOnFailed")
    fun setVisibleOnFailed(view: View, loginState: LoginState) {
        view.isVisible = loginState == LoginState.FAILED
    }
}
