package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
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
            return response.body() ?: throw JoinErrorResponse.Default()
        }
        throw JoinErrorResponse.Default()
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
                )
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                )
            )
        )
    }
}
