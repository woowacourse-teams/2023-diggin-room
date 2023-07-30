package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.RoomResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RoomService {

    @GET("/room")
    suspend fun findNext(
        @Header("cookie") token: String
    ): Response<RoomResponse>

    @POST("/scrap")
    suspend fun scrap(
        @Header("cookie") token: String
    ): Response<Void>

    @DELETE("/scrap")
    suspend fun cancelScrap(
        @Header("cookie") token: String
    ): Response<Void>
}
