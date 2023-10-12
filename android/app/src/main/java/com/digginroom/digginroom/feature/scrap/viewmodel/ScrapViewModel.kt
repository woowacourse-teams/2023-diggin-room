package com.digginroom.digginroom.feature.scrap.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.scrap.ScrappedRooms
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.util.SingleLiveEvent
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class ScrapViewModel @Keep constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    var rooms = ScrappedRooms(listOf())
    private val _uiState: SingleLiveEvent<ScrapUiState> = SingleLiveEvent()
    val uiState: LiveData<ScrapUiState>
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
        _uiState.value = ScrapUiState.Navigation(
            onSelect = ::startNavigation,
            rooms = rooms.value.map { it.toModel(selectable = true) },
            position = position
        )
    }

    fun startRoomSelection() {
        _uiState.value = ScrapUiState.Selection(
            onSelect = ::switchSelection,
            rooms = rooms.value.map { it.toModel(selectable = true) }
        )
    }

    private fun switchSelection(index: Int) {
        rooms.switchSelection(index)
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
            }.onFailure { }
        }
    }
}
