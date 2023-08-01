package com.digginroom.digginroom.feature.login

import android.widget.Button
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
}
