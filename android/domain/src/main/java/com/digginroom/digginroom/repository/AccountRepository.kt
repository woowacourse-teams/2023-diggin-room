package com.digginroom.digginroom.repository

import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id

interface AccountRepository {

    suspend fun saveAccount(account: Account): Result<Unit>

    suspend fun postAccount(account: Account): Result<String>

    suspend fun fetchIsDuplicatedId(id: Id): Result<Boolean>
}
