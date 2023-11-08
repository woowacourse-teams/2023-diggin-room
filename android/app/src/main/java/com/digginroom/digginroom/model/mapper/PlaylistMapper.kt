package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.PlaylistRequest
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist

object PlaylistMapper {

    private const val DEFAULT_TITLE_OF_PLAYLIST = "디깅룸 플레이리스트"

    fun Playlist.toExpectingTime(): Long = videosId.size.toLong()

    fun Playlist.toEntity(authCode: String): PlaylistRequest = PlaylistRequest(
        authCode = authCode,
        title = DEFAULT_TITLE_OF_PLAYLIST,
        videosId = videosId
    )
}
