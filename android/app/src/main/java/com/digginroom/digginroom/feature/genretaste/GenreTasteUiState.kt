package com.digginroom.digginroom.feature.genretaste

import com.digginroom.digginroom.model.GenreTasteSelectionModel

sealed interface GenreTasteUiState {

    data class InProgress(
        val genreTasteSelection: GenreTasteSelectionModel
    ) : GenreTasteUiState

    object Succeed : GenreTasteUiState

    object Failed : GenreTasteUiState
}
