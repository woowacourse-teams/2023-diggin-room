package com.digginroom.digginroom.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:onSingleClick")
    fun setOnSingleClickListener(view: View, onClicked: () -> Unit) {
        var previousClickedTime = 0L
        view.setOnClickListener {
            val clickedTime = System.currentTimeMillis()
            if (clickedTime - previousClickedTime >= 500L) {
                onClicked()
                previousClickedTime = clickedTime
            }
        }
    }
}
