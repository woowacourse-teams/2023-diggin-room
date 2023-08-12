package com.digginroom.digginroom.model.room.genre

class GenresTaste(value: List<GenreTaste>) {

    private val _value: MutableList<GenreTaste> = value.toMutableList()
    val value: List<GenreTaste>
        get() = _value

    fun switchSelection(genreTaste: GenreTaste) {
        val index = _value.indexOfFirst { it.genre == genreTaste.genre }

        _value.removeAt(index)
        _value.add(index, genreTaste.switchSelection())
    }

    fun endSurvey(): List<Genre> = _value.filter { it.isSelected }.map { it.genre }
}