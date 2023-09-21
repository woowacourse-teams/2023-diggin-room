package com.digginroom.digginroom.data.datasource.remote

import androidx.annotation.Keep
import com.digginroom.digginroom.data.di.Token
import com.digginroom.digginroom.data.entity.GenresTasteRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.service.GenreTasteService
import retrofit2.Response

class GenreTasteRemoteDataSource @Keep constructor(
    @Token private val genreTasteService: GenreTasteService
) {

    suspend fun post(genres: List<String>) {
        val response: Response<Void> = genreTasteService.post(GenresTasteRequest(genres))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 200) throw HttpError.Unknown(response)
    }
}
