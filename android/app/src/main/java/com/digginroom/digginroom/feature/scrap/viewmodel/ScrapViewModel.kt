package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.scrap.ScrappedRooms
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
import com.digginroom.digginroom.repository.ExtractionStateRepository
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.util.MutableSingleLiveData
import com.digginroom.digginroom.util.SingleLiveData
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class ScrapViewModel @Keep constructor(
    private val roomRepository: RoomRepository,
    private val extractionStateRepository: ExtractionStateRepository
) : ViewModel() {

    var rooms = ScrappedRooms(listOf())
    private val _uiState: MutableLiveData<ScrapUiState> = MutableLiveData()
    val uiState: LiveData<ScrapUiState>
        get() = _uiState

    private val _event: MutableSingleLiveData<ScrapUiEvent> =
        MutableSingleLiveData(ScrapUiEvent.Idle)
    val event: SingleLiveData<ScrapUiEvent>
        get() = _event

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
            }.onFailure {}
        }
    }

    private fun startNavigation(position: Int) {
        _uiState.value = ScrapUiState.Navigation(
            onSelect = ::startNavigation,
            rooms = rooms.value.map { it.toModel(selectable = false) },
            position = position
        )
    }

    fun startRoomSelection() {
        viewModelScope.launch {
            extractionStateRepository.fetch().onSuccess { isAvailable ->
                if (isAvailable) {
                    rooms = rooms.clear()
                    if (_uiState.value is ScrapUiState.Selection) {
                        _uiState.value = ScrapUiState.Default(
                            onSelect = ::startNavigation,
                            rooms = rooms.value.map { it.toModel(selectable = false) }
                        )
                    } else {
                        _uiState.value = ScrapUiState.Selection(
                            onSelect = ::switchSelection,
                            rooms = rooms.value.map { it.toModel(selectable = true) }
                        )
                    }
                } else {
                    _event.setValue(ScrapUiEvent.DisAllowedExtraction)
                }
            }.onFailure {}
        }
    }

    private fun switchSelection(index: Int) {
        if (!rooms.switchSelection(index)) {
            _event.setValue(ScrapUiEvent.FailedSelection(rooms.maxSelectingCount))
        }
        _uiState.value = ScrapUiState.Selection(
            onSelect = ::switchSelection,
            rooms = rooms.value.map { it.toModel(selectable = true) }
        )
    }

    fun startPlaylistExtraction() {
        _uiState.value = ScrapUiState.PlaylistExtraction(
            onSelect = ::switchSelection,
            rooms = rooms.value.map { it.toModel(selectable = true) }
        )
    }

    fun extractPlaylist(authCode: String) {
        _event.setValue(ScrapUiEvent.LoadingExtraction(rooms.selectedId.size))

        viewModelScope.launch {
            roomRepository.postPlaylist(
                authCode = authCode,
                playlist = Playlist(rooms.selectedId)
            ).onSuccess {
                rooms = rooms.clear()
                _uiState.value = ScrapUiState.Default(
                    rooms = rooms.value.map { it.toModel() },
                    onSelect = ::startNavigation
                )
            }.onFailure {}
        }
    }
}
