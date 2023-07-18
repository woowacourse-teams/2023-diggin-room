package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.model.room.Room
import com.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel(
    private val rooms: MutableList<Room>,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _cachedRoom: MutableLiveData<List<Room>> = MutableLiveData(rooms)
    val cachedRoom: LiveData<List<Room>>
        get() = _cachedRoom

    fun findNextRoom() {
        viewModelScope.launch {
            roomRepository.findNext().onSuccess { room ->
                rooms += room
                _cachedRoom.value = rooms
            }.onFailure {
            }
        }
    }
}
