package com.digginroom.digginroom.feature.room.bindingadapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.room.customview.roompager.PagingOrientation
import com.digginroom.digginroom.feature.room.customview.roompager.RoomPager
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState

object RoomPagerBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:roomPosition")
    fun roomPosition(roomPager: RoomPager, position: Int) {
        roomPager.updateRoomPosition(position)
    }

    @JvmStatic
    @BindingAdapter("app:orientation")
    fun orientation(roomPager: RoomPager, pagingOrientation: PagingOrientation) {
        roomPager.updateOrientation(pagingOrientation)
    }

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
    @BindingAdapter("app:onLoadNextRoom")
    fun onLoadNextRoom(roomPager: RoomPager, loadNextRoom: () -> Unit) {
        roomPager.loadNextRoom = loadNextRoom
    }
}
