package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.GenresTasteRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GenreTasteService {

    @POST("/member/favorite-genres")
    suspend fun post(
        @Body
        genresTaste: GenresTasteRequest
    ): Response<Void>
}
