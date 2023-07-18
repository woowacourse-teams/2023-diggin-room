package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.digginroom.digginroom.views.model.RoomModel

class RoomPagerViewModel : ViewModel() {
    private val _data: MutableLiveData<MutableList<RoomModel>> = MutableLiveData(mutableListOf())
    val data: LiveData<List<RoomModel>>
        get() = _data.map { it.toList() }

    fun addData(roomModel: RoomModel) {
        val data: MutableList<RoomModel> = _data.value?.toMutableList() ?: mutableListOf()
        data.add(roomModel)
        _data.value = data
    }
}
