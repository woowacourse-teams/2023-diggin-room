package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.service.RoomService
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song
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
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "_x3IbCecuuk",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "ovlka1CxCrY",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "_x3IbCecuuk",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "ovlka1CxCrY",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "_x3IbCecuuk",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "ovlka1CxCrY",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                song = Song(
                    title = "예시 제목",
                    albumTitle = "예시 앨범 타이틀",
                    artist = "예시 아티스트",
                    genres = List(3) {
                        Genre("예시 장르$it")
                    },
                    tags = List(5) {
                        "예시 태그$it"
                    }
                ),
                isScrapped = true
            )
        )
    }
}
