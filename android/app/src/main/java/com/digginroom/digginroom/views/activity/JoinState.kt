package com.digginroom.digginroom.views.activity

sealed interface JoinState {

    object Start : JoinState

    object Loading : JoinState

    object Succeed : JoinState

    data class Failed(val msg: String = DEFAULT_FAILED_MESSAGE) : JoinState

    companion object {

        private const val DEFAULT_FAILED_MESSAGE = "회원가입을 실패했습니다."
    }
}
