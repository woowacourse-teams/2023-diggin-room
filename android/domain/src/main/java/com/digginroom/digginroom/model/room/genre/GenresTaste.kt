package com.digginroom.digginroom.model.room.genre

class GenresTaste(
    value: List<GenreTaste>,
    private val maxSelectingCount: Int = 5
) {

    private val _value: MutableList<GenreTaste> = value.toMutableList()
    val value: List<GenreTaste>
        get() = _value

    val selected: List<Genre>
        get() = _value.filter { it.isSelected }.map { it.genre }

    private val selectingCount: Int
        get() = _value.filter { it.isSelected }.size

    fun switchSelection(genreTaste: GenreTaste) {
        if (!genreTaste.isSelected && selectingCount >= maxSelectingCount) return

        val index = _value.indexOfFirst { it.genre == genreTaste.genre }

        _value.removeAt(index)
        _value.add(index, genreTaste.switchSelection())
    }
}
