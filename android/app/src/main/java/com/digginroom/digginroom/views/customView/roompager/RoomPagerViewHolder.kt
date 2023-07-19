package com.digginroom.digginroom.views.customView.roompager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRoomPagerBinding
import com.digginroom.digginroom.views.model.RoomModel

class RoomPagerViewHolder(private val binding: ItemRoomPagerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var isLoaded: Boolean = false
    fun bind(roomModel: RoomModel) {
        if (isLoaded) {
            binding.room = roomModel
            binding.roomYoutubeView.navigate(roomModel)
        } else {
            binding.roomYoutubeView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    isLoaded = true
                    binding.roomYoutubeView.navigate(roomModel)
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RoomPagerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRoomPagerBinding.inflate(layoutInflater, parent, false)
            return RoomPagerViewHolder(binding)
        }
    }
}
