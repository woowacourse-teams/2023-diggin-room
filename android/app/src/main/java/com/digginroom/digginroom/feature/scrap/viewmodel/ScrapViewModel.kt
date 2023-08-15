package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class ScrapViewModel(
    private var rooms: List<Room>,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<List<RoomModel>> = MutableLiveData()
    val scrappedRooms: LiveData<List<RoomModel>>
        get() = _scrappedRooms

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped().onSuccess { scrappedRooms ->
                rooms = scrappedRooms
                _scrappedRooms.value = rooms.map { it.toModel() }
            }.onFailure {
            }
        }
    }
}
