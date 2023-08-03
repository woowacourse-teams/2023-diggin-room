package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.CancelScrapErrorResponse
import com.digginroom.digginroom.data.entity.CancelScrapRequest
import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrapErrorResponse
import com.digginroom.digginroom.data.entity.ScrapRequest
import com.digginroom.digginroom.data.service.RoomService
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import retrofit2.Response

class RoomRemoteDataSource(
    private val roomService: RoomService
) {
    suspend fun findNext(cookie: String): RoomResponse {
        val response: Response<RoomResponse> = roomService.findNext(cookie)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinErrorResponse.from(response.code())
        }
        throw JoinErrorResponse.from(response.code())
    }

    fun findScrapped(cookie: String = ""): List<Room> {
        return listOf(
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            )
        )
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
