package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer
import com.digginroom.digginroom.views.model.RoomModel

object RoomPlayerBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:room")
    fun room(youtubeRoomView: YoutubeRoomPlayer, room: RoomModel) {
        youtubeRoomView.navigate(room)
    }
}
