package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.views.model.RoomModel
import com.digginroom.digginroom.views.model.mapper.RoomMapper.toModel
import kotlinx.coroutines.launch

class RoomViewModel(
    private val rooms: MutableList<Room>,
    private val roomRepository: RoomRepository,
) : ViewModel() {

    private val _cachedRoom: MutableLiveData<List<RoomModel>> =
        MutableLiveData(rooms.map { it.toModel() })
    val cachedRoom: LiveData<List<RoomModel>>
        get() = _cachedRoom

    fun findNextRoom() {
        viewModelScope.launch {
            roomRepository.findNext().onSuccess { room ->
                rooms.add(room)
                _cachedRoom.value = rooms.map { it.toModel() }
            }.onFailure {
            }
        }
    }
}
