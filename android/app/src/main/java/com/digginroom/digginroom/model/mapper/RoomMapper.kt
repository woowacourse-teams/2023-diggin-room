package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrappedRoomsResponse
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.TrackMapper.toDomain
import com.digginroom.digginroom.model.mapper.TrackMapper.toModel
import com.digginroom.digginroom.model.room.Room

object RoomMapper {

    fun Room.toModel(): RoomModel {
        return RoomModel(
            videoId,
            isScrapped,
            track.toModel(),
            roomId,
            ScrapCountFormatter.convert(scrapCount)
        )
    }

    fun RoomResponse.toDomain(): Room {
        return Room(
            videoId,
            isScrapped,
            track.toDomain(),
            roomId,
            scrapCount
        )
    }

    fun ScrappedRoomsResponse.toDomain(): List<Room> {
        return scrappedRooms.map { it.toDomain() }
    }
}
