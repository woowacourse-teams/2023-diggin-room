package com.digginroom.digginroom.views

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(textView: TextView, isVisible: Boolean) {
        textView.isVisible = isVisible
    }
}
