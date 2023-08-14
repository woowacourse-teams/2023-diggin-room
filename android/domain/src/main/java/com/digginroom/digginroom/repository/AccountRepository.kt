package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.LoginResult

interface AccountRepository {

    suspend fun postJoin(account: Account): LogResult<Unit>

    suspend fun postLogIn(id: String, password: String): LogResult<LoginResult>

    suspend fun postLogin(idToken: String): LogResult<LoginResult>

    suspend fun fetchIsDuplicatedId(id: Id): LogResult<Boolean>
}
