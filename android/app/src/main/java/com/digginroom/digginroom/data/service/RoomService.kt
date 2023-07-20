package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.RoomResponse
import retrofit2.Response
import retrofit2.http.POST

interface RoomService {

    @POST("/room")
    suspend fun findNext(): Response<RoomResponse>
}
