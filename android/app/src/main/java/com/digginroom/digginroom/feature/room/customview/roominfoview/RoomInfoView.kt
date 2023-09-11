package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.customview.YoutubeRoomPlayerUIState
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

    fun setUIState(youtubeRoomPlayerUIState: YoutubeRoomPlayerUIState) {
        roomInfoBinding.roomInfoScrapToggle.scrapListener = {
            youtubeRoomPlayerUIState.onScrap.event(roomId)
        }

        roomInfoBinding.roomInfoScrapToggle.cancelScrapListener = {
            youtubeRoomPlayerUIState.onRemove.event(roomId)
        }
        roomInfoBinding.showRoomInfoListener = youtubeRoomPlayerUIState.showRoomInfo
        roomInfoBinding.showCommentsListener = youtubeRoomPlayerUIState.showComments
    }
}
