package com.digginroom.repository

import com.digginroom.model.user.Account

interface AccountRepository {

    suspend fun saveAccount(account: Account): Result<String>

    suspend fun postAccount(account: Account): Result<String>
}
