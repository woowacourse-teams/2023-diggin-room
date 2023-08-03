package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.DialogTrackInfoLayoutBinding
import com.digginroom.digginroom.databinding.RoomInfoBinding
import com.digginroom.digginroom.feature.room.ScrapListener
import com.digginroom.digginroom.model.RoomModel

class RoomInfoView(context: Context) : ConstraintLayout(context) {
    private val roomInfoBinding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)
    private val dialogBinding: DialogTrackInfoLayoutBinding =
        DialogTrackInfoLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        roomInfoBinding.showRoomInfoListener = DefaultShowDetailListener(context)
        roomInfoBinding.roomNavigator = DefaultRoomNavigator(context)
    }

    fun setRoomInfoListener(onScrapListener: ScrapListener) {
        roomInfoBinding.scrapToggle.scrapListener = onScrapListener
    }

    fun setRoomInfo(room: RoomModel) {
        roomInfoBinding.roomModel = room
        dialogBinding.roomModel = room
        setScrap(room.isScrapped)
    }

    private fun setScrap(isScrapped: Boolean) {
        roomInfoBinding.scrapToggle.isScrapped = isScrapped
    }
}
