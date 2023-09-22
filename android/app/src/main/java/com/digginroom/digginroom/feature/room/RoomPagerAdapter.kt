package com.digginroom.digginroom.feature.room

import android.content.Context
import com.digginroom.digginroom.feature.room.customview.roomplayer.YoutubeRoomPlayer
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.TrackModel
import com.dygames.roompager.Adapter

class RoomPagerAdapter(
    var rooms: List<RoomModel> = emptyList(),
    private val loadNextRoom: () -> Unit,
    private val openComment: (Long) -> Unit,
    private val openInfo: (TrackModel) -> Unit,
    private val openScrap: () -> Unit,
    private val scrap: (Long) -> Unit,
    private val unScrap: (Long) -> Unit
) : Adapter<YoutubeRoomPlayer> {

    private var viewHolders: List<YoutubeRoomPlayer> = emptyList()
    private var lastCurrentRoomPosition = 0

    override fun createViewHolder(context: Context): YoutubeRoomPlayer =
        YoutubeRoomPlayer(context) {
            play()
        }

    override fun getItemCount(): Int = rooms.size

    override fun onRecycle(
        currentRoomPosition: Int,
        recycledViewHolders: List<Adapter.ViewHolder>
    ) {
        viewHolders = recycledViewHolders.map { it as YoutubeRoomPlayer }
        lastCurrentRoomPosition = currentRoomPosition
        navigateRooms(currentRoomPosition)
        play()
    }

    override fun onLoadNextRoom() {
        loadNextRoom()
    }

    fun setData(data: List<RoomModel>) {
        rooms = data
        navigateRooms(lastCurrentRoomPosition)
    }

    fun play() {
        viewHolders.forEachIndexed { index, item ->
            if (index == viewHolders.size / 2) {
                item.play()
            } else {
                item.pause()
            }
        }
    }

    fun pause() {
        viewHolders.forEach {
            it.pause()
        }
    }

    private fun navigateRooms(currentRoomPosition: Int) {
        navigateRoom(-1, currentRoomPosition)
        navigateRoom(0, currentRoomPosition)
        navigateRoom(1, currentRoomPosition)
    }

    private fun navigateRoom(position: Int, currentRoomPosition: Int) {
        val center = 1
        val target = repeat(center + position, 3)
        if (target >= viewHolders.size) {
            return
        }
        val room = viewHolders[target]
        if (rooms.size > currentRoomPosition + position && currentRoomPosition + position >= 0) {
            room.navigate(
                RoomInfoUiState(
                    rooms[currentRoomPosition + position],
                    openComment,
                    openInfo,
                    openScrap,
                    scrap,
                    unScrap
                )
            )
        }
    }

    private fun repeat(n: Int, max: Int) = if (n >= max) {
        max % n
    } else if (n < 0) {
        (max - n) % n
    } else {
        n
    }
}
