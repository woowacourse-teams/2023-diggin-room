package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.RoomResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RoomService {

    @GET("/room")
    suspend fun findNext(
        @Header("cookie") token: String
    ): Response<RoomResponse>
}
