package com.digginroom.digginroom.repository

import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id

interface AccountRepository {

    suspend fun postJoin(account: Account): Result<Unit>

    suspend fun postLogIn(id: String, password: String): Result<String>

    suspend fun fetchIsDuplicatedId(id: Id): Result<Boolean>
}
