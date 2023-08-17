package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.mapper.TrackMapper.toDomain
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class ScrapRoomViewModel(
    private val rooms: MutableList<RoomModel>,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<RoomState> =
        MutableLiveData(RoomState.Success(rooms))

    val scrappedRooms: LiveData<RoomState>
        get() = _scrappedRooms

    fun postScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postScrapById(roomId)
                .onSuccess {
                }.onFailure {
                }
        }
    }

    fun removeScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.removeScrapById(roomId)
                .onSuccess {
                    rooms.forEachIndexed { index, room ->
                        if (room.roomId == roomId) {
                            rooms[index] = Room(
                                room.videoId,
                                false,
                                room.track.toDomain(),
                                roomId,
                                room.toDomain().scrapCount - 1
                            ).toModel()
                            _scrappedRooms.value = RoomState.Success(rooms)
                        }
                    }
                }.onFailure {
                }
        }
    }

    fun postDislike(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postDislike(roomId)
                .onSuccess {
                }.onFailure {
                }
        }
    }
}
