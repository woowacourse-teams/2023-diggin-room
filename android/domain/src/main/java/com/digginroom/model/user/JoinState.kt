package com.digginroom.model.user

sealed interface JoinState {

    data class Retry(val message: String) : JoinState

    object Success : JoinState
}
