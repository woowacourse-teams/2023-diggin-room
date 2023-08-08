package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.DislikeRequest
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import com.digginroom.digginroom.data.entity.ScrappedRoomsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST

interface RoomService {

    @GET("/room")
    suspend fun findNext(
        @Header("cookie") token: String
    ): Response<RoomResponse>

    @GET("/room/scrap")
    suspend fun findScrapped(
        @Header("cookie") token: String
    ): Response<ScrappedRoomsResponse>

    @POST("/room/scrap")
    suspend fun postScrapById(
        @Header("cookie") token: String,
        @Body scrapRequest: ScrapRequest
    ): Response<Void>

    @HTTP(method = "DELETE", path = "/room/scrap", hasBody = true)
    suspend fun removeScrapById(
        @Header("cookie") token: String,
        @Body cancelScrapRequest: CancelScrapRequest
    ): Response<Void>

    @POST("/room/dislike")
    suspend fun postDislike(
        @Header("cookie") token: String,
        @Body dislikeRequest: DislikeRequest
    ): Response<Void>
}
