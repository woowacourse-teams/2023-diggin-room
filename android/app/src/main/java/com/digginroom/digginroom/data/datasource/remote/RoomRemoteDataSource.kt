package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.CancelScrapErrorResponse
import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.DislikeErrorResponse
import com.digginroom.digginroom.data.entity.DislikeRequest
import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapErrorResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import com.digginroom.digginroom.data.entity.ScrappedRoomsResponse
import com.digginroom.digginroom.data.service.RoomService
import retrofit2.Response

class RoomRemoteDataSource(
    private val roomService: RoomService
) {
    suspend fun findNext(cookie: String): RoomResponse {
        val response: Response<RoomResponse> = roomService.findNext(cookie)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinErrorResponse.Default()
        }
        throw JoinErrorResponse.Default()
    }

    suspend fun postDislike(cookie: String, roomId: Long) {
        val response: Response<Void> = roomService.postDislike(cookie, DislikeRequest(roomId))

        if (!response.isSuccessful) {
            throw DislikeErrorResponse.from(response.code())
        }
    }

    suspend fun findScrapped(cookie: String): ScrappedRoomsResponse {
        val response: Response<ScrappedRoomsResponse> = roomService.findScrapped(cookie)

        if (response.isSuccessful) {
            return response.body() ?: throw RoomErrorResponse.NoSuchScrappedRooms()
        }
        throw RoomErrorResponse.Default()
    }

    suspend fun scrapById(cookie: String, roomId: Long) {
        val response: Response<Void> = roomService.scrapById(cookie, ScrapRequest(roomId))

        if (!response.isSuccessful) {
            throw ScrapErrorResponse.from(response.code())
        }
    }

    suspend fun cancelScrapById(cookie: String, roomId: Long) {
        val response: Response<Void> =
            roomService.cancelScrapById(cookie, CancelScrapRequest(roomId))

        if (!response.isSuccessful) {
            throw CancelScrapErrorResponse.from(response.code())
        }
    }
}
