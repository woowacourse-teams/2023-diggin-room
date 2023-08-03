package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.RoomInfoBinding
import com.digginroom.digginroom.feature.room.ScrapListener
import com.digginroom.digginroom.model.RoomModel

class RoomInfoView(context: Context) : ConstraintLayout(context) {
    private val roomInfoBinding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        roomInfoBinding.showRoomInfoListener = DefaultShowDetailListener(context)
        roomInfoBinding.roomNavigator = DefaultRoomNavigator(context)
    }
    fun setRoomInfo(room: RoomModel, onScrapListener: ScrapListener) {
        roomInfoBinding.roomModel = room
        roomInfoBinding.trackModel = room.trackModel
        roomInfoBinding.scrapToggle.scrapListener = {
            onScrapListener.scrap(room.roomId)
        }
        roomInfoBinding.scrapToggle.cancelScrapListener = {
            onScrapListener.cancelScrap(room.roomId)
        }
        setScrap(room.isScrapped)
    }

    private fun setScrap(isScrapped: Boolean) {
        roomInfoBinding.scrapToggle.isScrapped = isScrapped
    }
}
