package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.repository.AccountRepository

class DefaultAccountRepository(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun postJoin(account: Account): Result<Unit> =
        runCatching {
            accountRemoteDataSource.postJoin(
                id = account.id.value,
                password = account.password.value
            )
        }

    override suspend fun postLogIn(id: String, password: String): Result<String> =
        runCatching {
            accountRemoteDataSource.postLogin(
                id = id,
                password = password
            )
        }

    override suspend fun fetchIsDuplicatedId(id: Id): Result<Boolean> =
        runCatching {
            accountRemoteDataSource.fetchIsDuplicatedId(id.value).isDuplicated
        }
}
