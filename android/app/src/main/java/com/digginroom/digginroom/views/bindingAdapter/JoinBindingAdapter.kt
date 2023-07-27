package com.digginroom.digginroom.views.bindingAdapter

import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.activity.JoinState
import com.digginroom.digginroom.views.activity.ResultListener

object JoinBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["onClickForJoinState", "resultListener", "state"],
        requireAll = false
    )
    fun onClickForJoinState(
        button: Button,
        onClicked: () -> Unit,
        resultListener: ResultListener,
        state: JoinState
    ) {
        button.setOnClickListener {
            onClicked()
        }
        when (state) {
            is JoinState.Succeed -> resultListener.onSucceed()
            is JoinState.Failed -> resultListener.onFailed()
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleOnLoading")
    fun setVisibleOnLoading(view: View, state: JoinState) {
        view.isVisible = state == JoinState.Loading
    }
}
