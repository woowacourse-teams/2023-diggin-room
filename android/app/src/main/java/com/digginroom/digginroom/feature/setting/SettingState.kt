package com.digginroom.digginroom.feature.setting

sealed interface SettingState {

    sealed interface Logout : SettingState {

        object InProgress : Logout

        object Done : Logout
    }

    object Cancel : SettingState
}
