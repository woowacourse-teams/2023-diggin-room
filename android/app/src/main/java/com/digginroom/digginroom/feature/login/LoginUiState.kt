package com.digginroom.digginroom.feature.login

import com.digginroom.digginroom.model.AccountModel

sealed interface LoginUiState {

    sealed interface InProgress : LoginUiState {

        object Google : InProgress

        object KaKao : InProgress
    }

    object Loading : LoginUiState

    sealed interface Failed : LoginUiState {

        object AutoLogin : Failed

        object GuestLogin : Failed

        object SocialLogin : Failed

        data class Login(
            val account: AccountModel = AccountModel()
        ) : Failed
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
