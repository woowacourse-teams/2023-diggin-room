package com.digginroom.repository

import com.digginroom.model.user.Account

interface AccountRepository {

    suspend fun saveAccount(account: Account): Result<String>

    // todo: 로그인과 회원 가입의 차이점?
    suspend fun postAccount(account: Account): Result<String>
}
