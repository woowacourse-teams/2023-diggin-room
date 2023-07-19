package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.DefaultAccountRemoteDataSource
import com.digginroom.model.user.Account
import com.digginroom.model.user.Id
import com.digginroom.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultAccountRepository(
    private val accountRemoteDataSource: AccountRemoteDataSource = DefaultAccountRemoteDataSource(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountRepository {

    override suspend fun saveAccount(account: Account): Result<Unit> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.saveAccount(
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
}
