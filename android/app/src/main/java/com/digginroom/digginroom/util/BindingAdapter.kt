package com.digginroom.digginroom.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:src")
    fun setImageSrc(
        imageView: ImageView,
        @DrawableRes
        imageResId: Int
    ) {
        Glide.with(imageView.context)
            .load(imageResId)
            .centerCrop()
            .into(imageView)
    }

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
