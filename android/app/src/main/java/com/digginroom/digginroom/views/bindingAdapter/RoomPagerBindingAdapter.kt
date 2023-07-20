package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customview.roompager.RoomPager
import com.digginroom.digginroom.views.customview.roomview.RoomState

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

    @JvmStatic
    @BindingAdapter("app:onNextRoom")
    fun onNextRoom(roomPager: RoomPager, nextRoom: () -> Unit) {
        roomPager.onNextRoom = nextRoom
    }
}
