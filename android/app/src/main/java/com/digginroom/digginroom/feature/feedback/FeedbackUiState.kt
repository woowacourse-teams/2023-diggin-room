package com.digginroom.digginroom.feature.feedback

sealed class FeedbackUiState {

    object Cancel : FeedbackUiState()

    sealed class FeedbackUi : FeedbackUiState() {
        object Loading : FeedbackUi()
        object Failed : FeedbackUi()
        object Succeed : FeedbackUi()
    }
}
