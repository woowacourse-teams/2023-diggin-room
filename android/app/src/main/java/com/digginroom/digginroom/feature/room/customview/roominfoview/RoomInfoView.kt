package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoType
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.digginroom.digginroom.model.mapper.ScrapCountFormatter

class RoomInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val roomInfoType: RoomInfoType,
) : ConstraintLayout(context, attrs) {

    private val binding: ItemRoomInfoBinding =
        ItemRoomInfoBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initRoomInfoView()
        binding.scrapCountFormatter = ScrapCountFormatter
    }

    private fun initRoomInfoView() {
        if (roomInfoType == RoomInfoType.SCRAPPED) {
            with(binding) {
                roomInfoTvScrap.isVisible = true
                roomInfoIbBack.isVisible = true
                roomInfoIbScrap.isVisible = false
            }
        }
    }

    fun updateRoomInfoUiState(roomInfoUiState: RoomInfoUiState) {
        binding.roomInfoUiState = roomInfoUiState
    }
}
