package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.repository.RoomRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class ScrapViewModel @Keep constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<List<RoomModel>> = MutableLiveData()
    val scrappedRooms: LiveData<List<RoomModel>>
        get() = _scrappedRooms

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped().onSuccess { scrappedRooms ->
                _scrappedRooms.value = scrappedRooms.map { it.toModel() }
            }.onFailure {
            }
        }
    }
}
