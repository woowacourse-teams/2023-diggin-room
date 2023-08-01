package com.digginroom.digginroom.feature.join

import android.view.View
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.ResultListener

object JoinBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["joinStateListener", "joinState"],
        requireAll = false
    )
    fun onClickForLoginState(
        view: View,
        listener: ResultListener,
        joinState: JoinState
    ) {
        when (joinState) {
            is JoinState.Succeed -> listener.onSucceed()
            is JoinState.Failed -> listener.onFailed()
            else -> {}
        }
    }
}
