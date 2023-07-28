package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer
import com.digginroom.digginroom.views.model.RoomModel

class RoomRecycler(context: Context, private val gridSize: Int) : GridLayout(context) {

    var currentRoomPosition = 0
    private val roomPlayers: List<YoutubeRoomPlayer> =
        (0 until gridSize * gridSize).mapIndexed { index, view ->
            YoutubeRoomPlayer(context).apply {
                tag = index
            }
        }
    private var rooms: List<RoomModel> = emptyList()

    init {
        initLayout()
        initContentView()
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
        forEachIndexed { index, view ->
            view as YoutubeRoomPlayer
            if (index == target) {
                view.play()
            } else {
                view.pause()
            }
        }
    }

    fun navigateRooms(target: Int) {
        (getChildAt(target) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition])
        if (target - 1 >= 0 && rooms.size > currentRoomPosition - 1) {
            (getChildAt(target - 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition - 1])
        }
        if (target + 1 < gridSize * gridSize && rooms.size > currentRoomPosition + 1) {
            (getChildAt(target + 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition + 1])
        }
        if (target - gridSize >= 0 && rooms.size > currentRoomPosition - 1) {
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
