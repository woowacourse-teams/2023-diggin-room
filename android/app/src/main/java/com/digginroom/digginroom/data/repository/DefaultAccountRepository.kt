package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.DefaultAccountRemoteDataSource
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.model.user.Account
import com.digginroom.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultAccountRepository(
    private val accountRemoteDataSource: AccountRemoteDataSource = DefaultAccountRemoteDataSource(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountRepository {

    override suspend fun saveAccount(account: Account): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.saveAccount(
                    JoinRequest(
                        id = account.id.value,
                        password = account.password.value
                    )
                ).token
            }
        }

    override suspend fun postAccount(account: Account): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                accountRemoteDataSource.postAccount(
                    id = account.id.value,
                    password = account.password.value
                ).token
            }
        }
}
