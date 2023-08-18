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
import retrofit2.http.POST

interface RoomService {

    @GET("/room")
    suspend fun findNext(): Response<RoomResponse>

    @GET("/room/scrap")
    suspend fun findScrapped(): Response<ScrappedRoomsResponse>

    @POST("/room/scrap")
    suspend fun postScrapById(
        @Body scrapRequest: ScrapRequest
    ): Response<Void>

    @HTTP(method = "DELETE", path = "/room/scrap", hasBody = true)
    suspend fun removeScrapById(
        @Body cancelScrapRequest: CancelScrapRequest
    ): Response<Void>

    @POST("/room/dislike")
    suspend fun postDislike(
        @Body dislikeRequest: DislikeRequest
    ): Response<Void>
}
