package com.digginroom.repository

import com.digginroom.model.user.Account
import com.digginroom.model.user.Id

interface AccountRepository {

    suspend fun saveAccount(account: Account): Result<String>

    suspend fun fetchIsDuplicatedId(id: Id): Result<Boolean>
}
