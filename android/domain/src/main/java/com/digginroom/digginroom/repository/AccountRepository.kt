package com.digginroom.digginroom.repository

import com.digginroom.digginroom.model.user.Account

interface AccountRepository {

    suspend fun saveAccount(account: Account): Result<String>
}
