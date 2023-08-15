package com.digginroom.digginroom.feature.room.bindingadapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener
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
    @BindingAdapter("app:onLoadNext")
    fun onLoadNext(roomPager: RoomPager, callback: () -> Unit) {
        roomPager.loadNextRoom = callback
    }

    @JvmStatic
    @BindingAdapter("app:onScrap")
    fun onScrap(roomPager: RoomPager, callback: RoomEventListener) {
        roomPager.updateOnScrapListener(callback)
    }

    @JvmStatic
    @BindingAdapter("app:onRemoveScrap")
    fun onRemoveScrap(roomPager: RoomPager, callback: RoomEventListener) {
        roomPager.updateOnRemoveScrapListener(callback)
    }

    @JvmStatic
    @BindingAdapter("app:onDislike")
    fun onDislike(roomPager: RoomPager, callback: RoomEventListener) {
        roomPager.updateDislikeListener(callback)
    }

    @JvmStatic
    @BindingAdapter("app:onShowRoomInfoListener")
    fun onShowRoomInfoListener(roomPager: RoomPager, showRoomInfoListener: ShowRoomInfoListener) {
        roomPager.updateShowInfoListener(showRoomInfoListener)
    }

    @JvmStatic
    @BindingAdapter("app:showCommentsListener")
    fun showCommentsListener(roomPager: RoomPager, showCommentsListener: ShowCommentsListener) {
        roomPager.updateShowCommentsListener(showCommentsListener)
    }
}
