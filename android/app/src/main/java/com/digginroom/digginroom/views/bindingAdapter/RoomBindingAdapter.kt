package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customView.roomView.RoomYoutubeView
import com.digginroom.digginroom.views.model.RoomModel

object RoomBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:room")
    fun room(roomYoutubeView: RoomYoutubeView, room: RoomModel?) {
        if (room == null) return
        roomYoutubeView.navigate(room)
    }
}
