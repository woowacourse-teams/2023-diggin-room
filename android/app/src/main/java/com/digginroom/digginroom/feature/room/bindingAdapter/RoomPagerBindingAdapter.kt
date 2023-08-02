package com.digginroom.digginroom.feature.room.bindingAdapter

import androidx.databinding.BindingAdapter
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

    fun interface ScrapListener {
        fun onScrap(roomId: Long)
    }

    @JvmStatic
    @BindingAdapter("app:onScrapRoom")
    fun onScrapRoom(roomPager: RoomPager, scrapListener: ScrapListener) {
        roomPager.onScrap = {
            scrapListener.onScrap(it)
        }
    }
}
