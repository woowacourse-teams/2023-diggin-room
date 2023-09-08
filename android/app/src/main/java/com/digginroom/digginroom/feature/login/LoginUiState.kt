package com.digginroom.digginroom.feature.login

import com.digginroom.digginroom.model.AccountModel

sealed interface LoginUiState {

    object Loading : LoginUiState

    object Failed : LoginUiState {
        val account = AccountModel()
    }

    sealed interface Succeed : LoginUiState {

        object Surveyed : Succeed

        object NotSurveyed : Succeed

        companion object {

            fun from(hasSurveyed: Boolean): Succeed = if (hasSurveyed) {
                Surveyed
            } else {
                NotSurveyed
            }
        }
    }
}
