package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
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

    suspend fun findScrapped(cookie: String): ScrappedRoomsResponse {
        val response: Response<ScrappedRoomsResponse> = roomService.findScrapped(cookie)

        if (response.isSuccessful) {
            return response.body() ?: throw RoomErrorResponse.NoSuchScrappedRooms()
        }
        throw RoomErrorResponse.Default()
    }
}
