package com.digginroom.digginroom.feature.login

import com.digginroom.digginroom.model.AccountModel

data class LoginUiState(
    val accountModel: AccountModel,
    val loginState: LoginState
) {

    fun changeState(loginState: LoginState) =
        LoginUiState(
            accountModel = accountModel,
            loginState = loginState
        )
}
