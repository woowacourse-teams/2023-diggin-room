package com.digginroom.digginroom.feature.room.customview

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomPlayer
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.feature.room.customview.scrollpager.ScrollPager
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.RoomModel

class RoomRecycler(
    context: Context,
    private val gridSize: Int
) : GridLayout(context) {

    var currentRoomPosition = 0
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until gridSize).map {
        YoutubeRoomPlayer(context) {
            playCurrentRoomPlayer()
        }
    }
    private var rooms: List<RoomModel> = emptyList()

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

    fun updateOnFindCommentsListener(callback: RoomEventListener) {
        roomPlayers.forEach {
            it.updateOnFindCommentsListener(callback)
        }
    }

    fun updateComments(roomId: Long, comments: List<CommentModel>) {
        roomPlayers.forEach {
            it.updateComments(roomId, comments)
        }
    }

    fun updateShowCommentsListener(showCommentsListener: ShowCommentsListener) {
        roomPlayers.forEach {
            it.updateShowCommentsListener(showCommentsListener)
        }
    }

    fun recyclePrevRooms(scrollPager: ScrollPager) {
        val start = scrollPager.calculateStartChildPosition(gridSize)
        val end = scrollPager.calculateEndChildPosition(gridSize)
        val first = getChildAt(start)
        val second = getChildAt((gridSize * gridSize) / 2)
        val third = getChildAt(end)
        removeView(first)
        removeView(second)
        removeView(third)
        addView(third, start)
        addView(first, (gridSize * gridSize) / 2)
        addView(second, end)
    }

    fun recycleNextRooms(scrollPager: ScrollPager) {
        val start = scrollPager.calculateStartChildPosition(gridSize)
        val end = scrollPager.calculateEndChildPosition(gridSize)
        val first = getChildAt(start)
        val second = getChildAt((gridSize * gridSize) / 2)
        val third = getChildAt(end)
        removeView(first)
        removeView(second)
        removeView(third)
        addView(second, start)
        addView(third, (gridSize * gridSize) / 2)
        addView(first, end)
    }

    fun swapOrientation() {
        swapView(1, 3)
        swapView(5, 7)
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
    }

    fun playCurrentRoomPlayer() {
        repeat(gridSize * gridSize) {
            val view = getChildAt(it)
            if (view !is YoutubeRoomPlayer) return@repeat
            if (it == (gridSize * gridSize) / 2) {
                view.play()
            } else {
                view.pause()
            }
        }
    }

    fun navigateRooms() {
        val target = 1
        if (0 <= currentRoomPosition - 1) {
            navigateRoom(target, -1)
        }
        if (rooms.size > currentRoomPosition) {
            navigateRoom(target, 0)
        }
        if (rooms.size > currentRoomPosition + 1) {
            navigateRoom(target, 1)
        }
    }

    fun navigateFirstRoom() {
        currentRoomPosition = 0
    }

    fun navigateLastRoom() {
        currentRoomPosition = rooms.size - 1
    }

    fun roomSize() = rooms.size

    fun currentRoomPlayerRoomId(): Long =
        (getChildAt((gridSize * gridSize) / 2) as YoutubeRoomPlayer).currentRoomId

    fun pausePlayers() {
        repeat(gridSize * gridSize) {
            val view = getChildAt(it)
            if (view !is YoutubeRoomPlayer) return@repeat
            view.pause()
        }
    }

    private fun swapView(start: Int, end: Int) {
        val first = getChildAt(start)
        val second = getChildAt(end)
        removeView(first)
        removeView(second)
        addView(second, start)
        addView(first, end)
    }

    private fun navigateRoom(target: Int, position: Int) {
        val target = repeat(target + position, gridSize)
        val room = getChildAt((target * gridSize) + (gridSize / 2)) as? RoomPlayer
            ?: getChildAt((gridSize * (gridSize / 2)) + target) as? RoomPlayer
        room?.navigate(rooms[currentRoomPosition + position])
    }

    private fun repeat(n: Int, max: Int) = if (n >= max) {
        max % n
    } else if (n < 0) {
        (max - n) % n
    } else {
        n
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
        (0 until gridSize * gridSize).forEach {
            val view =
                if (it % gridSize == gridSize / 2) roomPlayers[it / gridSize] else View(context)
            view.layoutParams = LinearLayout.LayoutParams(
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels
            )
            addView(view)
        }
    }
}
