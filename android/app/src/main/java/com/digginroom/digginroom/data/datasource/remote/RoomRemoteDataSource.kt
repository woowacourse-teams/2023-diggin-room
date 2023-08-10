package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.data.entity.DislikeRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import com.digginroom.digginroom.data.entity.ScrappedRoomsResponse
import com.digginroom.digginroom.data.service.RoomService
import retrofit2.Response

class RoomRemoteDataSource(
    private val roomService: RoomService
) {

    suspend fun findNext(): RoomResponse {
        val response: Response<RoomResponse> = roomService.findNext()

        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }

    suspend fun findScrapped(): ScrappedRoomsResponse {
        val response: Response<ScrappedRoomsResponse> = roomService.findScrapped()

        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }

    suspend fun postScrapById(roomId: Long) {
        val response: Response<Void> = roomService.postScrapById(ScrapRequest(roomId))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 201) throw HttpError.Unknown(response)
    }

    suspend fun removeScrapById(roomId: Long) {
        val response: Response<Void> = roomService.removeScrapById(CancelScrapRequest(roomId))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 200) throw HttpError.Unknown(response)
    }

    suspend fun postDislike(roomId: Long) {
        val response: Response<Void> = roomService.postDislike(DislikeRequest(roomId))

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() != 201) throw HttpError.Unknown(response)
    }

    suspend fun findComments(roomId: Long): CommentsResponse {
        val response: Response<CommentsResponse> = roomService.findComments(roomId)

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() == 200) {
            return response.body()
                ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }
}
