package com.digginroom.digginroom.feature.scrap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.model.RoomModel

class ScrapRoomViewModel(
    rooms: List<RoomModel>,
    val initialPosition: Int
) : ViewModel() {

    private val _scrappedRooms: MutableLiveData<RoomState> =
        MutableLiveData(RoomState.Success(rooms))

    val scrappedRooms: LiveData<RoomState>
        get() = _scrappedRooms
}
