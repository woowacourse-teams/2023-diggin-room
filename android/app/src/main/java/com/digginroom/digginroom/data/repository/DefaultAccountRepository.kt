package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultAccountRepository(
    private val accountRemoteDataSource: AccountRemoteDataSource = AccountRemoteDataSource(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountRepository {

    override suspend fun postJoin(account: Account): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.postJoin(
                    id = account.id.value,
                    password = account.password.value
                )
            }
        }

    override suspend fun fetchIsDuplicatedId(id: Id): Result<Boolean> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.fetchIsDuplicatedId(id.value).isDuplicated
            }
        }

    override suspend fun postLogIn(account: Account): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.postLogin(
                    id = account.id.value,
                    password = account.password.value
                )
            }
        }
}
