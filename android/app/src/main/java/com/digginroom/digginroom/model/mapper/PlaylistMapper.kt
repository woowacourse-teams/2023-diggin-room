package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.PlaylistRequest
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
import com.digginroom.digginroom.model.room.scrap.playlist.Title

object PlaylistMapper {

    fun Playlist.toEntity(authCode: String): PlaylistRequest = PlaylistRequest(
        authCode = authCode,
        title = title.value,
        description = description,
        videosId = videosId
    )

    fun PlaylistRequest.toDomain(): Playlist = Playlist(
        title = Title(title),
        description = description,
        videosId = videosId
    )
}
