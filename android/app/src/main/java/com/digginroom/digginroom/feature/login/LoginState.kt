package com.digginroom.digginroom.feature.login

sealed interface LoginState {

    object Start : LoginState

    object Loading : LoginState

    object Failed : LoginState

    sealed interface Succeed : LoginState {
        object Surveyed : Succeed
        object NotSurveyed : Succeed

        companion object {

            fun from(hasSurveyed: Boolean): Succeed =
                if (hasSurveyed) {
                    Surveyed
                } else {
                    NotSurveyed
                }
        }
    }
}
