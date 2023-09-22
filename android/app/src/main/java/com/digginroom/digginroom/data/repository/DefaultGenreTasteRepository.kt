package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.remote.GenreTasteRemoteDataSource
import com.digginroom.digginroom.logging.DefaultLogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.repository.GenreTasteRepository

class DefaultGenreTasteRepository @Keep constructor(
    private val genreTasteRemoteDataSource: GenreTasteRemoteDataSource
) : GenreTasteRepository {

    override suspend fun post(genres: List<Genre>): DefaultLogResult<Unit> = logRunCatching {
        genreTasteRemoteDataSource.post(genres.map { it.title })
    }
}
