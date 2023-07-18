package com.digginroom.digginroom.views.bindingAdapter

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.activity.ResultListener

object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["onClick", "listener", "isSucceed"],
        requireAll = false,
    )
    fun onClickForResultListener(
        button: Button,
        onClick: () -> Unit,
        listener: ResultListener,
        isSucceed: Boolean,
    ) {
        button.setOnClickListener {
            onClick()
        }
        when (isSucceed) {
            true -> listener.onSucceed()
            false -> listener.onFailed()
        }
    }
}
