package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository

class ScrapViewModel(
    private val rooms: MutableList<Room> = mutableListOf(),
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<List<Room>> = MutableLiveData(rooms)
    private val scrappedRooms: LiveData<List<Room>>
        get() = _scrappedRooms

    fun findScrappedRooms() {
        _scrappedRooms.value = roomRepository.findScrapped()
    }
}
