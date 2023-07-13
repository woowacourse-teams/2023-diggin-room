package com.digginroom.digginroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.DefaultUserRepository
import com.digginroom.model.join.JoinState
import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository

class JoinViewModel(
    private val userRepository: UserRepository
//    private val joinSucceed: () -> Unit,
//    private val joinFailed: () -> Unit
) : ViewModel() {

    private val _state: MutableLiveData<JoinState> = MutableLiveData()
    val state: LiveData<JoinState>
        get() = _state

    fun join(id: String, password: String) {
        _state.value = userRepository.save(
            User(
                id = id,
                password = password
            )
        )
    }

    companion object {

        fun getJoinViewModelFactory(): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    JoinViewModel(
                        userRepository = DefaultUserRepository()
//                        joinSucceed = joinSucceed,
//                        joinFailed = joinFailed
                    )
                }
            }
        }
    }
}
