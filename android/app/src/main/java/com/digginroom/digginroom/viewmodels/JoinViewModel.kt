package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.DefaultUserRepository
import com.digginroom.model.join.JoinState
import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository

class JoinViewModel(
    private val userRepository: UserRepository,
    private val joinSucceed: () -> Unit,
    private val joinFailed: () -> Unit
) : ViewModel() {

    fun join(id: String, password: String) {
        val joinState = userRepository.save(
            User(
                id = id,
                password = password
            )
        )
        when (joinState) {
            is JoinState.Success -> joinSucceed()
            is JoinState.Retry -> joinFailed()
        }
    }

    companion object {

        fun getJoinViewModelFactory(
            joinSucceed: () -> Unit,
            joinFailed: () -> Unit
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    JoinViewModel(
                        userRepository = DefaultUserRepository(),
                        joinSucceed = joinSucceed,
                        joinFailed = joinFailed
                    )
                }
            }
        }
    }
}