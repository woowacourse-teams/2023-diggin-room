package com.digginroom.digginroom.feature.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel(
    private val rooms: MutableList<Room>,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _cachedRoom: MutableLiveData<RoomState> = MutableLiveData(RoomState.Loading)
    val cachedRoom: LiveData<RoomState>
        get() = _cachedRoom

    fun findNext() {
        _cachedRoom.value = RoomState.Loading
//        viewModelScope.launch {
//            roomRepository.findNext().onSuccess { room ->
//                rooms.add(room)
//                _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
//            }.onFailure {
//                _cachedRoom.value = RoomState.Error(it)
//            }
//        }
        val rooms = listOf(
            Room(
                "k2qpczGTg6U",
                false,
                Track("라일락", "아이유", Genre("댄스")),
                0,
                2
            ),
            Room(
                "KZGBQunqT8k",
                true,
                Track("OMG", "뉴진스", Genre("댄스")),
                1,
                4
            ),
            Room(
                "SAO1OAXoQL0",
                false,
                Track("롤러코스터", "엔믹스", Genre("댄스")),
                1,
                20
            )
        )
        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
    }

    fun postDislike(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postDislike(roomId).onSuccess {}.onFailure {}
        }
    }

    fun postScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postScrapById(roomId).onSuccess {
                rooms.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        rooms[index] =
                            Room(room.videoId, true, room.track, roomId, room.scrapCount + 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {
            }
        }
    }

    fun removeScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.removeScrapById(roomId).onSuccess {
                rooms.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        rooms[index] =
                            Room(room.videoId, false, room.track, roomId, room.scrapCount - 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {}
        }
    }
}
