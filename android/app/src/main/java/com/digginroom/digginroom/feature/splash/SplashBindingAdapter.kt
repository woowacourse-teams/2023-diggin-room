package com.digginroom.digginroom.feature.splash

import android.view.View
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.ResultListener

object SplashBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["tokenStateListener", "tokenState"],
        requireAll = false
    )
    fun setOnTokenStateListener(
        view: View,
        listener: ResultListener,
        tokenState: TokenState
    ) {
        when (tokenState) {
            TokenState.VALID -> listener.onSucceed()
            TokenState.INVALID -> listener.onFailed()
            else -> {}
        }
    }
}
