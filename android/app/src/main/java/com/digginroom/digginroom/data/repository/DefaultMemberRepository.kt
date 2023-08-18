package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.MemberRemoteDataSource
import com.digginroom.digginroom.logging.DefaultLogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.user.Member
import com.digginroom.digginroom.repository.MemberRepository

class DefaultMemberRepository(
    private val memberRemoteDataSource: MemberRemoteDataSource
) : MemberRepository {

    override suspend fun fetch(token: String): DefaultLogResult<Member> = logRunCatching {
        val memberResponse = memberRemoteDataSource.fetch(token)

        Member(memberResponse.hasSurveyed)
    }
}
