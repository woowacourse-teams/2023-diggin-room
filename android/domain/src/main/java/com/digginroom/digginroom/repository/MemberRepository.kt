package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.user.Member

interface MemberRepository {

    suspend fun postGenresTaste(genres: List<Genre>): LogResult<Unit>
    suspend fun fetch(token: String): LogResult<Member>
}
