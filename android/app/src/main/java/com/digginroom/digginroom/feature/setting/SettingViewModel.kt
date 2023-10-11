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

    private val _state: MutableLiveData<SettingState> = MutableLiveData()
    val state: LiveData<SettingState>
        get() = _state

    fun startLogout() {
        _state.value = SettingState.Logout.InProgress
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.delete()
                .onSuccess {
                    _state.value = SettingState.Logout.Done
                }.onFailure {}
        }
    }

    fun cancel() {
        _state.value = SettingState.Cancel
    }
}
