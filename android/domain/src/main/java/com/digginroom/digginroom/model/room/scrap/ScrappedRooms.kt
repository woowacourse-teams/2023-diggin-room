package com.digginroom.digginroom.model.room.scrap

class ScrappedRooms(
    value: List<ScrappedRoom>
) {

    private val _value: MutableList<ScrappedRoom> = value.toMutableList()
    val value: List<ScrappedRoom>
        get() = _value

    val selected: List<ScrappedRoom>
        get() = _value.filter { it.isSelected }

    fun switchSelection(index: Int) {
        val room = _value[index]

        _value.removeAt(index)
        _value.add(index, room.switchSelection())
    }
}
