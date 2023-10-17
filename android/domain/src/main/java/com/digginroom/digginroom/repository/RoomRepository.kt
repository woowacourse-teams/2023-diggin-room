package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist

interface RoomRepository {

    suspend fun findNext(): LogResult<Room>
    suspend fun findScrapped(): LogResult<List<ScrappedRoom>>
    suspend fun postScrapById(roomId: Long): LogResult<Unit>
    suspend fun removeScrapById(roomId: Long): LogResult<Unit>
    suspend fun postDislike(roomId: Long): LogResult<Unit>
    suspend fun postPlaylist(authCode: String, playlist: Playlist): LogResult<Unit>
}
