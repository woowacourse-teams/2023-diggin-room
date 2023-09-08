package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.navigator.DefaultRoomNavigator
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.ScrapCountFormatter

class RoomInfoView(context: Context) : ConstraintLayout(context) {
    val roomId: Long
        get() = roomInfoBinding.room?.roomId ?: throw IllegalStateException()

    private val roomInfoBinding: ItemRoomInfoBinding =
        ItemRoomInfoBinding.inflate(LayoutInflater.from(context), this, true)

    fun setRoomInfo(
        room: RoomModel
    ) {
        roomInfoBinding.room = room
        roomInfoBinding.scrapCountFormatter = ScrapCountFormatter
        roomInfoBinding.roomNavigator = DefaultRoomNavigator(context)
    }

    fun updateOnScrapListener(callback: RoomEventListener) {
        roomInfoBinding.roomInfoScrapToggle.scrapListener = {
            callback.event(roomId)
        }
    }

    fun updateOnRemoveScrapListener(callback: RoomEventListener) {
        roomInfoBinding.roomInfoScrapToggle.cancelScrapListener = {
            callback.event(roomId)
        }
    }

    fun updateOnShowRoomInfoListener(showRoomInfoListener: ShowRoomInfoListener) {
        roomInfoBinding.showRoomInfoListener = showRoomInfoListener
    }

    fun updateShowCommentsListener(showCommentsListener: ShowCommentsListener) {
        roomInfoBinding.showCommentsListener = showCommentsListener
    }
}
