package com.digginroom.digginroom.feature.room

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.feature.tutorial.TutorialState
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.repository.TutorialRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class RoomViewModel @Keep constructor(
    private val roomRepository: RoomRepository,
    private val tutorialRepository: TutorialRepository
) : ViewModel() {

    private val rooms: MutableList<Room> = mutableListOf()
    private val _cachedRoom: MutableLiveData<RoomState> = MutableLiveData(RoomState.Loading)
    val cachedRoom: LiveData<RoomState>
        get() = _cachedRoom
    private val _tutorialCompleted: MutableLiveData<TutorialState> = MutableLiveData()
    val tutorialCompleted: LiveData<TutorialState> get() = _tutorialCompleted

    fun findNext() {
        _cachedRoom.value = RoomState.Loading
        viewModelScope.launch {
            roomRepository.findNext().onSuccess { room ->
                rooms.add(room)
                _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
            }.onFailure {
                _cachedRoom.value = RoomState.Error(it)
            }
        }
    }

    fun postDislike(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postDislike(roomId).onSuccess {}.onFailure {}
        }
    }

    fun postScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.postScrapById(roomId).onSuccess {
                rooms.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        rooms[index] =
                            Room(room.videoId, true, room.track, roomId, room.scrapCount + 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {
            }
        }
    }

    fun removeScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.removeScrapById(roomId).onSuccess {
                rooms.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        rooms[index] =
                            Room(room.videoId, false, room.track, roomId, room.scrapCount - 1)
                        _cachedRoom.value = RoomState.Success(rooms.map { it.toModel() })
                    }
                }
            }.onFailure {}
        }
    }

    fun completeTutorial() {
        _tutorialCompleted.value = TutorialState.Loading
        viewModelScope.launch {
            tutorialRepository.save(true).onSuccess {
                _tutorialCompleted.value = TutorialState.Success(true)
            }.onFailure { _tutorialCompleted.value = TutorialState.Error(it) }
        }
    }

    fun fetchTutorialCompleted() {
        _tutorialCompleted.value = TutorialState.Loading
        viewModelScope.launch {
            tutorialRepository.fetch().onSuccess {
                _tutorialCompleted.value = TutorialState.Success(it)
            }.onFailure { _tutorialCompleted.value = TutorialState.Error(it) }
        }
    }
}
