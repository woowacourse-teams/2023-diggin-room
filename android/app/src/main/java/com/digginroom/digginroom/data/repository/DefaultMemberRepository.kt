package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.MemberRemoteDataSource
import com.digginroom.digginroom.logging.DefaultLogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.repository.MemberRepository

class DefaultMemberRepository(
    private val memberRemoteDataSource: MemberRemoteDataSource
) : MemberRepository {

    override suspend fun postGenresTaste(genres: List<Genre>): DefaultLogResult<Unit> = logRunCatching {
        memberRemoteDataSource.postGenresTaste(genres.map { it.title })
    }
}
