package com.digginroom.digginroom.feature.join

import com.digginroom.digginroom.model.JoinAccountModel
import com.digginroom.digginroom.model.JoinVerificationModel

sealed interface JoinUiState {

    data class InProgress(
        val joinVerification: JoinVerificationModel
    ) : JoinUiState

    object Loading : JoinUiState

    object Cancel : JoinUiState

    data class Failed(
        val account: JoinAccountModel = JoinAccountModel(),
        val joinVerification: JoinVerificationModel = JoinVerificationModel()
    ) : JoinUiState

    object Succeed : JoinUiState
}
