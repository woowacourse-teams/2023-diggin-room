package com.digginroom.digginroom.feature.setting

sealed interface SettingUiState {

    sealed interface Logout : SettingUiState {

        object InProgress : Logout

        object Done : Logout
    }

    object Cancel : SettingUiState
}
