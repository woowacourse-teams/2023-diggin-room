package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapErrorResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import com.digginroom.digginroom.data.service.RoomService
import retrofit2.Response

class RoomRemoteDataSource(
    private val roomService: RoomService,
) {
    suspend fun findNext(cookie: String): RoomResponse {
        val response: Response<RoomResponse> = roomService.findNext(cookie)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinErrorResponse.from(response.code())
        }
        throw JoinErrorResponse.from(response.code())
    }

    suspend fun scrapById(cookie: String, roomId: Long) {
        val response: Response<Void> = roomService.scrapById(cookie, ScrapRequest(roomId))

        if (!response.isSuccessful) {
            throw ScrapErrorResponse.from(response.code())
        }
    }
}
