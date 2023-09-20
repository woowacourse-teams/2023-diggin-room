package com.digginroom.digginroom.feature.tutorial

sealed class TutorialUiState {
    data class Error(val throwable: Throwable) : TutorialUiState()
    object Loading : TutorialUiState()
    data class Success(val tutorialCompleted: Boolean) : TutorialUiState()
}
