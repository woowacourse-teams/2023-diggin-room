package com.digginroom.digginroom.views.bindingAdapter

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.activity.ResultListener

object JoinBindingAdapter {

    // todo: false로하면 왜 타입 애러가 생길까요?
    @JvmStatic
    @BindingAdapter(
        value = ["onClickWithResult", "resultListener", "flag"],
        requireAll = false
    )
    fun onClickWithResult(
        button: Button,
        onClicked: () -> Unit,
        resultListener: ResultListener,
        flag: Boolean
    ) {
        button.setOnClickListener {
            onClicked()
        }
        when (flag) {
            true -> resultListener.onSucceed()
            false -> resultListener.onFailed()
        }
    }
}
