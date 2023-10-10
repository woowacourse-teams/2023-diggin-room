package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.ScrappedRoomModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom
import com.digginroom.digginroom.model.room.scrap.ScrappedRooms
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.util.MutableSingleLiveData
import com.digginroom.digginroom.util.SingleLiveData
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class ScrapViewModel @Keep constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    var rooms = ScrappedRooms(listOf())
    private val _uiState: MutableSingleLiveData<ScrapUiState> = MutableSingleLiveData()
    val uiState: SingleLiveData<ScrapUiState>
        get() = _uiState

    private val _scrappedRooms: MutableLiveData<List<ScrappedRoom>> = MutableLiveData()
    val scrappedRooms: LiveData<List<ScrappedRoomModel>>
        get() = _scrappedRooms.map {
            it.map { scrappedRoom ->
                scrappedRoom.toModel()
            }
        }

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped().onSuccess { scrappedRooms ->
                rooms = ScrappedRooms(scrappedRooms)
                _uiState.setValue(
                    ScrapUiState.Default(
                        rooms = rooms.value.map { it.toModel() },
                        onClick = ::startNavigation
                    )
                )
                _scrappedRooms.value = rooms.value
            }.onFailure {
            }
        }
    }

    private fun startNavigation(index: Int) {
        _uiState.setValue(
            ScrapUiState.Navigation(
                rooms = rooms.value.map { it.toModel() },
                targetIndex = index,
                onClick = ::startNavigation
            )
        )
    }

    fun startPlaylistExtraction() {
        _uiState.setValue(
            ScrapUiState.Extraction(
                rooms = rooms.value.map { it.toModel() },
                onClick = ::switchSelection
            )
        )
    }

    private fun switchSelection(index: Int) {
        rooms.switchSelection(index)

        _uiState.setValue(
            ScrapUiState.Extraction(
                rooms = rooms.value.map { it.toModel() },
                onClick = ::switchSelection
            )
        )
//        _scrappedRooms.value = rooms.value
    }
}
