package com.digginroom.digginroom.feature.tutorial

sealed class TutorialState {
    data class Error(val throwable: Throwable) : TutorialState()
    object Loading : TutorialState()
    data class Success(val tutorialCompleted: Boolean) : TutorialState()
}
