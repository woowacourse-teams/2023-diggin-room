package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import retrofit2.Response
import retrofit2.http.Body
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
    suspend fun scrapById(
        @Header("cookie") token: String,
        @Body scrapRequest: ScrapRequest
    ): Response<Void>

    @DELETE("/scrap")
    suspend fun cancelScrapById(
        @Header("cookie") token: String,
        @Body cancelScrapRequest: CancelScrapRequest
    ): Response<Void>
}
