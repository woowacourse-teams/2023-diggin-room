package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.GenresTasteRequest
import com.digginroom.digginroom.data.entity.MemberResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberService {

    @POST("/member/favorite-genres")
    suspend fun postGenresTaste(
        @Body
        genresTaste: GenresTasteRequest
    ): Response<Void>

    @GET("/member/me")
    suspend fun fetch(
        @Header("Cookie")
        token: String
    ): Response<MemberResponse>
}
