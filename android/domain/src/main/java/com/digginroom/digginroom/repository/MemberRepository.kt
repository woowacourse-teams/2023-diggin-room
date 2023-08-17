package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.user.Member

interface MemberRepository {

    suspend fun fetch(token: String): LogResult<Member>
}
