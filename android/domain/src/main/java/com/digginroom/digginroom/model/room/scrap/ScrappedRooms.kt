package com.digginroom.digginroom.model.room.scrap

class ScrappedRooms(
    value: List<ScrappedRoom>,
    val maxSelectingCount: Int = 10
) {

    private val _value: MutableList<ScrappedRoom> = value.toMutableList()
    val value: List<ScrappedRoom>
        get() = _value

    val isSelected: Boolean
        get() = _value.any { it.isSelected }

    val selectedId: List<String>
        get() = _value.filter {
            it.isSelected
        }.map {
            it.room.videoId
        }

    private val selectingCount: Int
        get() = _value.filter { it.isSelected }.size

    fun switchSelection(index: Int): Boolean {
        if (!value[index].isSelected && selectingCount >= maxSelectingCount) return false

        val room = _value[index]

        _value.removeAt(index)
        _value.add(index, room.switchSelection())
        return true
    }

    fun clear(): ScrappedRooms = ScrappedRooms(
        _value.map { it.copy(isSelected = false) }
    )
}
