package com.digginroom.digginroom.feature.room.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.R
import com.digginroom.digginroom.feature.room.RoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.ScrapToggle

object RoomInfoBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:isScrapped")
    fun isScrapped(scrapToggle: ScrapToggle, isScrapped: Boolean) {
        scrapToggle.isScrapped = isScrapped
        if (isScrapped) {
            scrapToggle.setImageResource(R.drawable.scrap_icon)
        } else {
            scrapToggle.setImageResource(R.drawable.unscrap_icon)
        }
    }
}
