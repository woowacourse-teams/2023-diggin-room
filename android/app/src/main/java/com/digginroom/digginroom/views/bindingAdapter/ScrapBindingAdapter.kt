package com.digginroom.digginroom.views.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digginroom.digginroom.R
import com.digginroom.digginroom.views.activity.ScrapAdapter
import com.digginroom.digginroom.views.model.RoomModel

object ScrapBindingAdapter {

    private const val THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg"

    @JvmStatic
    @BindingAdapter("app:items")
    fun setItems(
        recyclerView: RecyclerView,
        items: List<RoomModel>
    ) {
        (recyclerView.adapter as? ScrapAdapter)?.submitList(items.toMutableList())
    }

    @JvmStatic
    @BindingAdapter("app:thumbnail")
    fun setThumbnail(
        imageView: ImageView,
        videoId: String
    ) {
        Glide.with(imageView.context)
            .load(THUMBNAIL_URL.format(videoId))
            .fallback(R.drawable.ic_error_24)
            .error(R.drawable.ic_error_24)
            .into(imageView)
    }
}
