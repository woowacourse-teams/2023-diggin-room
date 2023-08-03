package com.digginroom.digginroom.feature.room.customview

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.ScrapListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.feature.room.customview.scrollpager.ScrollPager
import com.digginroom.digginroom.model.RoomModel

class RoomRecycler(
    context: Context,
    private val gridSize: Int
) : GridLayout(context) {

    var currentRoomPosition = 0
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until gridSize * gridSize).map {
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

    fun setRoomInfoListener(onScrapListener: ScrapListener) {
        roomPlayers.forEach {
            it.setRoomInfoListener(onScrapListener)
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
        if (target in (0 until gridSize * gridSize) && rooms.size > currentRoomPosition) {
            (getChildAt(target) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition])
        }
        if (target - 1 >= 0 && 0 <= currentRoomPosition - 1) {
            (getChildAt(target - 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition - 1])
        }
        if (target + 1 < gridSize * gridSize && rooms.size > currentRoomPosition + 1) {
            (getChildAt(target + 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition + 1])
        }
        if (target - gridSize >= 0 && 0 <= currentRoomPosition - 1) {
            (getChildAt(target - gridSize) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition - 1])
        }
        if (target + gridSize < gridSize * gridSize && rooms.size > currentRoomPosition + 1) {
            (getChildAt(target + gridSize) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition + 1])
        }
    }

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
