package com.digginroom.digginroom.feature.room.customview

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.feature.room.customview.scrollpager.ScrollPager
import com.digginroom.digginroom.model.RoomModel

class RoomRecycler(
    context: Context,
    private val gridSize: Int
) : GridLayout(context) {

    var currentRoomPosition = 0
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until gridSize).map {
        YoutubeRoomPlayer(context) {
            playCurrentRoomPlayer(currentRoomPlayerPosition)
        }
    }
    private var rooms: List<RoomModel> = emptyList()
    private var currentRoomPlayerPosition: Int = 0

    init {
        initLayout()
        initContentView()
    }

    fun updateOnScrapListener(callback: RoomEventListener) {
        roomPlayers.forEach {
            it.updateOnScrapListener(callback)
        }
    }

    fun updateOnRemoveScrapListener(callback: RoomEventListener) {
        roomPlayers.forEach {
            it.updateOnRemoveScrapListener(callback)
        }
    }

    fun updateShowRoomInfoListener(showRoomInfoListener: ShowRoomInfoListener) {
        roomPlayers.forEach {
            it.updateShowRoomInfoListener(showRoomInfoListener)
        }
    }

    fun recyclePreviousRooms(scrollPager: ScrollPager) {
        repeat(gridSize) {
            val child = getChildAt(scrollPager.calculateEndChildPosition(it, gridSize))
            removeView(child)
            addView(child, scrollPager.calculateStartChildPosition(it, gridSize))
        }
    }

    fun recycleNextRooms(scrollPager: ScrollPager) {
        repeat(gridSize) {
            val child = getChildAt(scrollPager.calculateStartChildPosition(it, gridSize))
            removeView(child)
            addView(child, scrollPager.calculateEndChildPosition(it, gridSize))
        }
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
    }

    fun playCurrentRoomPlayer(target: Int) {
        currentRoomPlayerPosition = target
        repeat(gridSize * gridSize) {
            val room = getChildAt(it) as YoutubeRoomPlayer
            if (it == target) {
                room.play()
            } else {
                room.pause()
            }
        }
    }

    fun navigateRooms(target: Int) {
        val target =
            if (target >= gridSize) gridSize % target else if (target < 0) gridSize else target

        if (rooms.size > currentRoomPosition) {
            roomPlayers[target].navigate(rooms[currentRoomPosition])
        }
        if (0 <= currentRoomPosition - 1) {
            if (target >= gridSize) {
                roomPlayers[0].navigate(rooms[currentRoomPosition - 1])
            }
        }
        if (rooms.size > currentRoomPosition + 1) {
            roomPlayers[2].navigate(rooms[currentRoomPosition + 1])
        }
    }

    fun navigateFirstRoom() {
        currentRoomPosition = 0
    }

    fun navigateLastRoom() {
        currentRoomPosition = rooms.size - 1
    }

    fun currentRoomPlayerRoomId(): Long = roomPlayers[currentRoomPlayerPosition].currentRoomId

    private fun initLayout() {
        columnCount = gridSize
        rowCount = gridSize
        layoutParams = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initContentView() {
        roomPlayers.map {
            it.layoutParams = LinearLayout.LayoutParams(
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels
            )
            addView(it)
        }
    }
}
