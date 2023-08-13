package com.digginroom.digginroom.feature.genretaste

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.genretaste.adpater.GenreTasteAdapter
import com.digginroom.digginroom.model.GenreTasteModel

object GenreTasteBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:items")
    fun setItems(recyclerView: RecyclerView, items: List<GenreTasteModel>?) {
        (recyclerView.adapter as GenreTasteAdapter).submitList(items?.toMutableList())
    }

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
    @BindingAdapter(
        value = ["surveyStateListener", "surveyState"],
        requireAll = false
    )
    fun onClickForSurveyState(
        view: View,
        listener: ResultListener,
        surveyState: GenreTasteSurveyState
    ) {
        when (surveyState) {
            GenreTasteSurveyState.SUCCEED -> listener.onSucceed()
            GenreTasteSurveyState.FAILED -> listener.onFailed()
            else -> {}
        }
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
