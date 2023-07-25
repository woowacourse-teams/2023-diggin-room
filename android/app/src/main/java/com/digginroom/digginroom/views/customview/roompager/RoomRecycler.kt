package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer

class RoomRecycler(context: Context, private val gridSize: Int) : GridLayout(context) {
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until gridSize * gridSize).map {
        YoutubeRoomPlayer(context)
    }

    init {
        initLayout()
        initContentView()
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
