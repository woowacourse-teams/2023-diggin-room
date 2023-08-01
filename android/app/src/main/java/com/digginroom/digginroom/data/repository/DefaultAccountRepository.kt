package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.repository.AccountRepository

class DefaultAccountRepository(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun postJoin(account: Account): LogResult<Unit> =
        logRunCatching {
            accountRemoteDataSource.postJoin(
                id = account.id.value,
                password = account.password.value
            )
        }

    override suspend fun postLogIn(id: String, password: String): LogResult<String> =
        logRunCatching {
            accountRemoteDataSource.postLogin(
                id = id,
                password = password
            )
        }

    override suspend fun fetchIsDuplicatedId(id: Id): LogResult<Boolean> =
        logRunCatching {
            accountRemoteDataSource.fetchIsDuplicatedId(id.value).isDuplicated
        }
}
