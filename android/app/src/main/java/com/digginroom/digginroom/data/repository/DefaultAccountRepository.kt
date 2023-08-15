package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Member
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

    override suspend fun postLogIn(id: String, password: String): LogResult<Member> =
        logRunCatching {
            val loginResult = accountRemoteDataSource.postLogin(
                id = id,
                password = password
            )

            Member(
                hasSurveyed = loginResult.hasFavorite,
                token = loginResult.token
            )
        }

    override suspend fun postLogin(idToken: String): LogResult<Member> =
        logRunCatching {
            val loginResult = accountRemoteDataSource.postLogin(idToken)

            Member(
                hasSurveyed = loginResult.hasFavorite,
                token = loginResult.token
            )
        }

    override suspend fun fetchIsDuplicatedId(id: Id): LogResult<Boolean> =
        logRunCatching {
            accountRemoteDataSource.fetchIsDuplicatedId(id.value).isDuplicated
        }
}
