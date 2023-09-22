package com.digginroom.digginroom.feature.join

import com.digginroom.digginroom.model.JoinAccountModel
import com.digginroom.digginroom.model.JoinVerificationModel

sealed class JoinUiState(
    open val joinVerification: JoinVerificationModel = JoinVerificationModel()
) {

    data class InProgress(
        override val joinVerification: JoinVerificationModel
    ) : JoinUiState(joinVerification)

    object Loading : JoinUiState()

    object Cancel : JoinUiState()

    data class Failed(
        val account: JoinAccountModel = JoinAccountModel(),
        override val joinVerification: JoinVerificationModel = JoinVerificationModel()
    ) : JoinUiState(joinVerification)

    object Succeed : JoinUiState()
}
