package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.RoomInfoBinding
import com.digginroom.digginroom.model.RoomModel

class RoomInfo(context: Context, private val onScrapClickListener: () -> Unit) : ConstraintLayout(context) {
    private val binding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)
    var layoutHeight = 0
        private set

    init {
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        layoutHeight = measuredHeight
    }

    fun setRoomInfo(room: RoomModel) {
        binding.roomModel = room
        setScrap(room.isScrapped)
    }

    private fun setScrap(isScrapped: Boolean) {
        binding.scrapToggle.isScrapped = isScrapped
        binding.scrapToggle.onScrapClickListener = onScrapClickListener
    }
}
