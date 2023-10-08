package com.digginroom.digginroom.feature.setting

sealed interface SettingState {

    sealed interface Logout : SettingState {

        object InProgress : Logout

        object Done : Logout
    }

    object Cancel : SettingState

    // todo: 피드백 기능에 대한 상태
    object Feedback : SettingState
}
