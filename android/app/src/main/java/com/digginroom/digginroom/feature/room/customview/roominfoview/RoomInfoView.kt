package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoType
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.digginroom.digginroom.model.mapper.ScrapCountFormatter

class RoomInfoView @JvmOverloads constructor(
    context: Context,
    roomInfoType: RoomInfoType,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ItemRoomInfoBinding =
        ItemRoomInfoBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.roomInfoType = roomInfoType
        binding.scrapCountFormatter = ScrapCountFormatter
    }

    fun updateRoomInfoUiState(roomInfoUiState: RoomInfoUiState) {
        binding.roomInfoUiState = roomInfoUiState
    }
}
