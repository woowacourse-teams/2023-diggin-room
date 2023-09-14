package com.digginroom.digginroom.feature.genretaste

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GenreTasteBindingAdapter {

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
    @BindingAdapter("app:hasBrightness")
    fun setImageBrightness(
        imageView: ImageView,
        hasBrightness: Boolean
    ) {
        if (hasBrightness) {
            imageView.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)
        } else {
            imageView.colorFilter = null
        }
    }
}
