package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customView.roomView.RoomYoutubeView
import com.digginroom.model.room.Room

object RoomBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:nextRoom")
    fun nextRoom(roomYoutubeView: RoomYoutubeView, room: Room?) {
        if (room == null) return
        roomYoutubeView.navigate(room)
    }
}
