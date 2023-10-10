package com.digginroom.digginroom.feature.scrap.viewmodel

import com.digginroom.digginroom.model.ScrappedRoomModel

sealed interface ScrapUiState {

    val rooms: List<ScrappedRoomModel>
    val onClick: (position: Int) -> Unit

    data class Default(
        override val rooms: List<ScrappedRoomModel>,
        override val onClick: (position: Int) -> Unit
    ) : ScrapUiState

    data class Navigation(
        override val rooms: List<ScrappedRoomModel>,
        val targetIndex: Int,
        override val onClick: (position: Int) -> Unit
    ) : ScrapUiState

    data class Extraction(
        override val rooms: List<ScrappedRoomModel>,
        override val onClick: (position: Int) -> Unit
    ) : ScrapUiState
}
