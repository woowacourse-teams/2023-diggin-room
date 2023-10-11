package com.digginroom.digginroom.feature.scrap.viewmodel

import com.digginroom.digginroom.model.ScrappedRoomModel

sealed interface ScrapUiState {

    val rooms: List<ScrappedRoomModel>
    val onSelect: (position: Int) -> Unit

    data class Default(
        override val rooms: List<ScrappedRoomModel>,
        override val onSelect: (position: Int) -> Unit
    ) : ScrapUiState

    data class Navigation(
        override val rooms: List<ScrappedRoomModel>,
        val position: Int,
        override val onSelect: (position: Int) -> Unit
    ) : ScrapUiState

    data class Extraction(
        override val rooms: List<ScrappedRoomModel>,
        override val onSelect: (position: Int) -> Unit
    ) : ScrapUiState
}
