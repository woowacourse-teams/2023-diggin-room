package com.digginroom.digginroom.feature.setting

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.repository.TokenRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class SettingViewModel @Keep constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<SettingUiState> = MutableLiveData()
    val uiState: LiveData<SettingUiState>
        get() = _uiState

    fun startLogout() {
        _uiState.value = SettingUiState.Logout.InProgress
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.delete()
                .onSuccess {
                    _uiState.value = SettingUiState.Logout.Done
                }.onFailure {}
        }
    }

    fun cancel() {
        _uiState.value = SettingUiState.Cancel
    }
}
