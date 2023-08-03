package com.digginroom.digginroom.feature.scrap

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
    private var rooms: MutableList<Room>,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<List<RoomModel>> = MutableLiveData(listOf())
    val scrappedRooms: LiveData<List<RoomModel>>
        get() = _scrappedRooms

    init {
        findScrappedRooms()
    }

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped()
                .onSuccess {
                    rooms.addAll(it)
                    _scrappedRooms.value = rooms.map { room ->
                        room.toModel()
                    }
                }.onFailure {
                }
        }
    }
}
