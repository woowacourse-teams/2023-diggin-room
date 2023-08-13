package com.digginroom.digginroom.feature.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.repository.RoomRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _tokenState: MutableLiveData<TokenState> = MutableLiveData(TokenState.NOT_VALIDATED)
    val tokenState: LiveData<TokenState>
        get() = _tokenState

    fun validateToken() {
        viewModelScope.launch {
            Log.d("woogi", "validateToken: executed")
            roomRepository.findNext().onSuccess {
                _tokenState.value = TokenState.VALID
            }.onFailure {
                Log.d("woogi", "validateToken: onFailure")
                _tokenState.value = TokenState.INVALID
            }
            Log.d("woogi", "validateToken: ${_tokenState.value}")
        }
    }
}
