package com.digginroom.digginroom.feature.room

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom
import com.digginroom.digginroom.repository.RoomRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class RoomViewModel @Keep constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val rooms: MutableList<Room> = mutableListOf()
    private val _cachedRoom: MutableLiveData<RoomState> = MutableLiveData(RoomState.Loading)
    val cachedRoom: LiveData<RoomState>
        get() = _cachedRoom

    fun findNext() {
        _cachedRoom.value = RoomState.Loading
        viewModelScope.launch {
            roomRepository.findNext().onSuccess { room ->
                rooms.add(room)
                _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
            }.onFailure {
                _cachedRoom.value = RoomState.Error(it)
            }
        }
    }

    fun setRooms(rooms: List<Room>) {
        this.rooms.clear()
        this.rooms.addAll(rooms)
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
                            room.copy(isScrapped = true, scrapCount = room.scrapCount + 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {}
        }
    }

    fun removeScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.removeScrapById(roomId).onSuccess {
                rooms.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        rooms[index] =
                            room.copy(isScrapped = false, scrapCount = room.scrapCount - 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {}
        }
    }

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped().onSuccess { scrappedRooms ->
                updateScrappedRooms(scrappedRooms)
                _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
            }.onFailure { }
        }
    }

    private fun updateScrappedRooms(
        scrappedRooms: List<ScrappedRoom>
    ) {
        rooms.forEachIndexed { index, room ->
            if (room.isScrapped && scrappedRooms.find { scrappedRoom -> room.roomId == scrappedRoom.room.roomId } == null) {
                rooms[index] =
                    room.copy(isScrapped = false, scrapCount = room.scrapCount - 1)
            }
        }
    }
}
