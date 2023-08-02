package com.digginroom.digginroom.feature.room.bindingAdapter

import android.util.Log
import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.room.RoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roompager.RoomPager
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState

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
    @BindingAdapter("app:onLoadNextRoom")
    fun onLoadNextRoom(roomPager: RoomPager, loadNextRoom: () -> Unit) {
        roomPager.loadNextRoom = loadNextRoom
    }

    @JvmStatic
    @BindingAdapter("app:onRoomInfoListener")
    fun onRoomInfoListener(roomPager: RoomPager, onRoomInfoListener: RoomInfoListener) {
        roomPager.setRoomInfoListener(onRoomInfoListener)
    }
}
