package com.digginroom.digginroom.feature.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel(
    private val rooms: MutableList<Room>,
    private val roomRepository: RoomRepository
) : ViewModel(), RoomInfoListener {

    private val _cachedRoom: MutableLiveData<RoomState> =
        MutableLiveData(RoomState.Loading)
    val cachedRoom: LiveData<RoomState>
        get() = _cachedRoom

    fun findNextRoom() {
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

    override fun scrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.scrapById(roomId)
        }
    }

    override fun cancelScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.cancelScrapById(roomId)
        }
    }
}
