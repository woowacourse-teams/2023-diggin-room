package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song
import com.digginroom.digginroom.repository.RoomRepository

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : RoomRepository {
    override suspend fun findNext(): LogResult<Room> {
        return logRunCatching {
            roomRemoteDataSource.findNext(tokenLocalDataSource.fetch()).toDomain()
        }
    }

    override suspend fun findScrapped(): Result<List<Room>> {
        return Result.success(
            listOf(
                Room(
                    videoId = "r8kxr39UVg8",
                    song = Song(
                        title = "예시 제목",
                        albumTitle = "예시 앨범 타이틀",
                        artist = "예시 아티스트",
                        genres = listOf(
                            Genre("예시 장르 1"),
                            Genre("예시 장르 2"),
                            Genre("예시 장르 3")
                        ),
                        tags = listOf(
                            "예시 태그 1",
                            "예시 태그 2",
                            "예시 태그 3",
                            "예시 태그 4",
                            "예시 태그 5"
                        )
                    ),
                    isScrapped = true
                ),
                Room(
                    videoId = "cfES5D7CbMA",
                    song = Song(
                        title = "예시 제목",
                        albumTitle = "예시 앨범 타이틀",
                        artist = "예시 아티스트",
                        genres = listOf(
                            Genre("예시 장르 1"),
                            Genre("예시 장르 2"),
                            Genre("예시 장르 3")
                        ),
                        tags = listOf(
                            "예시 태그 1",
                            "예시 태그 2",
                            "예시 태그 3",
                            "예시 태그 4",
                            "예시 태그 5"
                        )
                    ),
                    isScrapped = true
                ),
                Room(
                    videoId = "13aOJlKChVI",
                    song = Song(
                        title = "예시 제목",
                        albumTitle = "예시 앨범 타이틀",
                        artist = "예시 아티스트",
                        genres = listOf(
                            Genre("예시 장르 1"),
                            Genre("예시 장르 2"),
                            Genre("예시 장르 3")
                        ),
                        tags = listOf(
                            "예시 태그 1",
                            "예시 태그 2",
                            "예시 태그 3",
                            "예시 태그 4",
                            "예시 태그 5"
                        )
                    ),
                    isScrapped = true
                ),
                Room(
                    videoId = "L6MkDEKLq0g",
                    song = Song(
                        title = "예시 제목",
                        albumTitle = "예시 앨범 타이틀",
                        artist = "예시 아티스트",
                        genres = listOf(
                            Genre("예시 장르 1"),
                            Genre("예시 장르 2"),
                            Genre("예시 장르 3")
                        ),
                        tags = listOf(
                            "예시 태그 1",
                            "예시 태그 2",
                            "예시 태그 3",
                            "예시 태그 4",
                            "예시 태그 5"
                        )
                    ),
                    isScrapped = true
                ),
                Room(
                    videoId = "VtAieLbbxek",
                    song = Song(
                        title = "예시 제목",
                        albumTitle = "예시 앨범 타이틀",
                        artist = "예시 아티스트",
                        genres = listOf(
                            Genre("예시 장르 1"),
                            Genre("예시 장르 2"),
                            Genre("예시 장르 3")
                        ),
                        tags = listOf(
                            "예시 태그 1",
                            "예시 태그 2",
                            "예시 태그 3",
                            "예시 태그 4",
                            "예시 태그 5"
                        )
                    ),
                    isScrapped = true
                )
            )
        )
    }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
