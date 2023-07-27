package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.graphics.Point
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.digginroom.digginroom.views.customview.roomview.RoomPlayer
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer
import com.digginroom.digginroom.views.model.RoomModel

class RoomRecycler(context: Context, private val gridSize: Int) : GridLayout(context) {

    var currentRoomPosition = 0
    var onNextRoom: () -> Unit = { }
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until gridSize * gridSize).map {
        YoutubeRoomPlayer(context)
    }
    private var targetRooms: List<Point> = listOf(Point(0, 0), Point(0, 1), Point(1, 0))
    private var rooms: List<RoomModel> = emptyList()

    init {
        initLayout()
        initContentView()
    }

    fun recycleView(scrollPager: ScrollPager) {
        repeat(gridSize) {
            val child = getChildAt(scrollPager.calculateEndChildPosition(it, gridSize))
            removeView(child)
            addView(child, scrollPager.calculateStartChildPosition(it, gridSize))
        }
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        navigateRooms()
    }

    fun playCurrentRoomPlayer(target: Int) {
        forEachIndexed { index, view ->
            if (index == target) {
                (view as YoutubeRoomPlayer).play()
            } else {
                (view as YoutubeRoomPlayer).pause()
            }
        }
    }

    private fun navigateRooms() {
        targetRooms.forEachIndexed { index, point ->
            val recyclePosition = currentRoomPosition + index - 1
            if (recyclePosition in rooms.indices) {
                (getChildAt(pointToScalar(point)) as RoomPlayer).navigate(
                    rooms[recyclePosition]
                )
            }
        }
    }

    private fun pointToScalar(point: Point): Int {
        return point.y * gridSize + point.x
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
