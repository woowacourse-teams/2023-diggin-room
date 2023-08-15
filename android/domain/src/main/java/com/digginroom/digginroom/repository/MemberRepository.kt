package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.room.genre.Genre

interface MemberRepository {

    suspend fun postGenresTaste(genres: List<Genre>): LogResult<Unit>
}
