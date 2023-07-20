package com.digginroom.digginroom.views.bindingAdapter

import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.activity.LoginState
import com.digginroom.digginroom.views.activity.ResultListener

object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["onClick", "listener", "loginState"],
        requireAll = false
    )
    fun onClickForResultListener(
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
