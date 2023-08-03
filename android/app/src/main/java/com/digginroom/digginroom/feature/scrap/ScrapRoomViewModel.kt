package com.digginroom.digginroom.feature.scrap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.ScrapListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class ScrapRoomViewModel(
    rooms: List<RoomModel>,
    private val roomRepository: RoomRepository
) : ViewModel(), ScrapListener {

    private val _scrappedRooms: MutableLiveData<RoomState> =
        MutableLiveData(RoomState.Success(rooms))

    val scrappedRooms: LiveData<RoomState>
        get() = _scrappedRooms

    override fun scrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.scrapById(roomId)
                .onSuccess {
                }.onFailure {
                }
        }
    }

    override fun cancelScrap(roomId: Long) {
        viewModelScope.launch {
            roomRepository.cancelScrapById(roomId)
                .onSuccess {
                }.onFailure {
                }
        }
    }
}
