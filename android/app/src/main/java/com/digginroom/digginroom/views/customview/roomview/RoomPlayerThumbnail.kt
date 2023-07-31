package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.digginroom.digginroom.views.model.RoomModel

class RoomPlayerThumbnail(
    context: Context
) : AppCompatImageView(context) {

    fun load(room: RoomModel) {
        turnOn()
        Glide.with(this).load("https://img.youtube.com/vi/${room.videoId}/0.jpg").into(this)
    }

    fun turnOff() {
        visibility = GONE
    }

    private fun turnOn() {
        visibility = VISIBLE
    }
}
