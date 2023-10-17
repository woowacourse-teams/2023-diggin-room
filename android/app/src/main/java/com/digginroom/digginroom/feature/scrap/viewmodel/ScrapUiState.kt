package com.digginroom.digginroom.feature.scrap.viewmodel

import com.digginroom.digginroom.model.ScrappedRoomModel

sealed interface ScrapUiState {

    val onSelect: (position: Int) -> Unit
    val rooms: List<ScrappedRoomModel>

    data class Default(
        override val onSelect: (position: Int) -> Unit,
        override val rooms: List<ScrappedRoomModel>
    ) : ScrapUiState

    data class Navigation(
        override val onSelect: (position: Int) -> Unit,
        override val rooms: List<ScrappedRoomModel>,
        val position: Int
    ) : ScrapUiState

    data class Selection(
        override val onSelect: (position: Int) -> Unit,
        override val rooms: List<ScrappedRoomModel>
    ) : ScrapUiState

    data class PlaylistExtraction(
        override val onSelect: (position: Int) -> Unit = {},
        override val rooms: List<ScrappedRoomModel>
    ) : ScrapUiState
}
