package com.digginroom.digginroom.feature.room.customview.scrollpager

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.model.RoomModel

class ScrappedRoomRecycler(
    context: Context,
    private val gridSize: Int
) : GridLayout(context) {

    var currentRoomPosition = 0
    private val roomPlayers: List<YoutubeRoomPlayer> = List(gridSize) {
        YoutubeRoomPlayer(context) {}.apply {
            tag = it
        }
    }
    private var rooms: List<RoomModel> = emptyList()

    init {
        initLayout()
        initContentView()
    }

    fun recyclePreviousRooms() {
        val child = getChildAt(gridSize - 1)
        removeView(child)
        addView(child, 0)
    }

    fun recycleNextRooms() {
        val child = getChildAt(0)
        removeView(child)
        addView(child, gridSize - 1)
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
    }

    fun playCurrentRoomPlayer(target: Int) {
        forEachIndexed { index, view ->
            view as YoutubeRoomPlayer
            if (index == target) {
                view.play()
                Log.d("woogi", "play")
            } else {
                view.pause()
                Log.d("woogi", "pause")
            }
        }
    }

    private fun initLayout() {
        columnCount = 1
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

    fun navigateRooms(target: Int) {
        (getChildAt(target) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition])

        if (target - 1 >= 0) {
            (getChildAt(target - 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition - 1])
        }

        if (target + 1 < gridSize * gridSize) {
            (getChildAt(target + 1) as YoutubeRoomPlayer).navigate(rooms[currentRoomPosition + 1])
        }
    }
}
