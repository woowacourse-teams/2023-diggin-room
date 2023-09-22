package com.digginroom.digginroom.feature.genretaste

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object GenreTasteBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:hasDarkness")
    fun setImageFilter(
        imageView: ImageView,
        hasBrightness: Boolean
    ) {
        if (hasBrightness) {
            imageView.setColorFilter(Color.parseColor("#858585"), PorterDuff.Mode.MULTIPLY)
        } else {
            imageView.colorFilter = null
        }
    }
}
