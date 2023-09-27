package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.entity.MemberToken
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Member
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.TokenRepository

class DefaultAccountRepository @Keep constructor(
    private val tokenRepository: TokenRepository,
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
            val memberToken: MemberToken = accountRemoteDataSource.postLogin(
                id = id,
                password = password
            )

            tokenRepository.save(memberToken.token)
            Member(memberToken.hasSurveyed)
        }

    override suspend fun postGuestLogin(): LogResult<Member> =
        logRunCatching {
            val memberToken: MemberToken = accountRemoteDataSource.postGuestLogin()

            tokenRepository.save(memberToken.token)
            Member(memberToken.hasSurveyed)
        }

    override suspend fun postSocialLogin(idToken: String): LogResult<Member> =
        logRunCatching {
            val memberToken: MemberToken = accountRemoteDataSource.postSocialLogin(idToken)

            tokenRepository.save(memberToken.token)
            Member(memberToken.hasSurveyed)
        }

    override suspend fun fetchIsDuplicatedId(id: Id): LogResult<Boolean> =
        logRunCatching {
            accountRemoteDataSource.fetchIsDuplicatedId(id.value).isDuplicated
        }
}
