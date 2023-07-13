package com.digginroom.model.join

sealed interface JoinState {

    data class Retry(val message: String) : JoinState

    object Success : JoinState
}
