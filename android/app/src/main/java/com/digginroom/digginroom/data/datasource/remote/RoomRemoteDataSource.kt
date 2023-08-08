package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.CancelScrapErrorResponse
import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.DislikeErrorResponse
import com.digginroom.digginroom.data.entity.DislikeRequest
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

    suspend fun findNext(): RoomResponse {
        val response: Response<RoomResponse> = roomService.findNext()

        if (response.isSuccessful) {
            return response.body() ?: throw RoomErrorResponse.Default()
        }
        throw RoomErrorResponse.Default()
    }

    suspend fun findScrapped(): ScrappedRoomsResponse {
        val response: Response<ScrappedRoomsResponse> = roomService.findScrapped()

        if (response.isSuccessful) {
            return response.body() ?: throw RoomErrorResponse.NoSuchScrappedRooms()
        }
        throw RoomErrorResponse.Default()
    }

    suspend fun postScrapById(roomId: Long) {
        val response: Response<Void> = roomService.postScrapById(ScrapRequest(roomId))

        if (!response.isSuccessful) {
            throw ScrapErrorResponse.from(response.code())
        }
    }

    suspend fun removeScrapById(roomId: Long) {
        val response: Response<Void> =
            roomService.removeScrapById(CancelScrapRequest(roomId))

        if (!response.isSuccessful) {
            throw CancelScrapErrorResponse.from(response.code())
        }
    }

    suspend fun postDislike(roomId: Long) {
        val response: Response<Void> = roomService.postDislike(DislikeRequest(roomId))

        if (!response.isSuccessful) {
            throw DislikeErrorResponse.from(response.code())
        }
    }
}
