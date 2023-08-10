package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id

interface AccountRepository {

    suspend fun postJoin(account: Account): LogResult<Unit>

    suspend fun postLogIn(id: String, password: String): LogResult<String>

    suspend fun postLogin(idToken: String): LogResult<String>

    suspend fun fetchIsDuplicatedId(id: Id): LogResult<Boolean>

}
