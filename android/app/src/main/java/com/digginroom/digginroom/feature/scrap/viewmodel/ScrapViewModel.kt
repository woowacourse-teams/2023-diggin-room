package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
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

    fun findScrappedRooms() {
        viewModelScope.launch {
            roomRepository.findScrapped().onSuccess { scrappedRooms ->
                rooms = ScrappedRooms(scrappedRooms)
                _uiState.setValue(
                    ScrapUiState.Default(
                        rooms = rooms.value.map { it.toModel() },
                        onSelect = ::startNavigation
                    )
                )
            }.onFailure {
            }
        }
    }

    private fun startNavigation(position: Int) {
        _uiState.setValue(
            ScrapUiState.Navigation(
                rooms = rooms.value.map { it.toModel() },
                position = position,
                onSelect = ::startNavigation
            )
        )
    }

    fun startPlaylistExtraction() {
        _uiState.setValue(
            ScrapUiState.Extraction(
                rooms = rooms.value.map { it.toModel(selectable = true) },
                onSelect = ::switchSelection
            )
        )
    }

    private fun switchSelection(index: Int) {
        rooms.switchSelection(index)
        _uiState.setValue(
            ScrapUiState.Extraction(
                rooms = rooms.value.map { it.toModel(selectable = true) },
                onSelect = ::switchSelection
            )
        )
    }
}
