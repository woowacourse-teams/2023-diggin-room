package com.digginroom.digginroom.model.room.genre

data class GenreTaste(
    val genre: Genre,
    val isSelected: Boolean
) {

    fun switchSelection(): GenreTaste = GenreTaste(
        genre = genre,
        isSelected = !isSelected
    )
}
