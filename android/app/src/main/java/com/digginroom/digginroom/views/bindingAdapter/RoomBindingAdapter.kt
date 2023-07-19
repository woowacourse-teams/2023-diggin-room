package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customView.roomview.YoutubeRoomView
import com.digginroom.digginroom.views.model.RoomModel

object RoomBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:room")
    fun room(youtubeRoomView: YoutubeRoomView, room: RoomModel) {
        youtubeRoomView.navigate(room)
    }
}
