package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customView.roompager.RoomPager
import com.digginroom.digginroom.views.customView.roomview.RoomState

object RoomPagerBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:rooms")
    fun rooms(roomPager: RoomPager, roomState: RoomState) {
        when (roomState) {
            is RoomState.Error -> {
            }
            RoomState.Loading -> {
            }
            is RoomState.Success -> {
                roomPager.updateData(roomState.rooms)
            }
        }
    }
}
