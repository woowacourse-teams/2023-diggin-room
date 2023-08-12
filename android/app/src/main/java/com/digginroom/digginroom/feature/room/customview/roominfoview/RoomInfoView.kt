package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.RoomInfoBinding
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.navigator.DefaultRoomNavigator
import com.digginroom.digginroom.model.RoomModel

class RoomInfoView(context: Context) : ConstraintLayout(context) {
    val roomId: Long
        get() = roomInfoBinding.room?.roomId ?: throw IllegalStateException()

    private val roomInfoBinding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)

    fun setRoomInfo(
        room: RoomModel
    ) {
        roomInfoBinding.room = room
        roomInfoBinding.roomNavigator = DefaultRoomNavigator(context)
    }

    fun updateOnScrapListener(callback: RoomEventListener) {
        roomInfoBinding.scrapToggle.scrapListener = {
            callback.event(roomId)
        }
    }

    fun updateOnRemoveScrapListener(callback: RoomEventListener) {
        roomInfoBinding.scrapToggle.cancelScrapListener = {
            callback.event(roomId)
        }
    }

    fun updateOnShowRoomInfoListener(showRoomInfoListener: ShowRoomInfoListener) {
        roomInfoBinding.showRoomInfoListener = showRoomInfoListener
    }

    fun updateOnFindCommentsListener(callback: RoomEventListener) {
        roomInfoBinding.findCommentsListener = callback
    }
}
