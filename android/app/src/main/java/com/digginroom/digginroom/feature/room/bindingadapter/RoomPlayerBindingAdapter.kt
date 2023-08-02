package com.digginroom.digginroom.feature.room.bindingadapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.model.RoomModel

object RoomPlayerBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:room")
    fun room(youtubeRoomView: YoutubeRoomPlayer, room: RoomModel) {
        youtubeRoomView.navigate(room)
    }
}
