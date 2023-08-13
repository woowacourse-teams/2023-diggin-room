package com.digginroom.digginroom.feature.splash

import android.util.Log
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
        Log.d("woogi", "setOnTokenStateListener: executed")
        when (tokenState) {
            TokenState.VALID -> {
                Log.d("woogi", "validateToken: valid")
                listener.onSucceed()
            }
            TokenState.INVALID -> {
                Log.d("woogi", "validateToken: invalid")
                listener.onFailed()
            }
            else -> {
                Log.d("woogi", "validateToken: none")
            }
        }
    }
}
