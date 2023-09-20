package com.digginroom.digginroom.model

data class GenreTasteSelectionModel(
    val genresTaste: List<GenreTasteModel>,
    val onSelect: (GenreTasteModel) -> Unit
)
