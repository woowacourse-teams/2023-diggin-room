package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.mapper.SongMapper.toDomain
import com.digginroom.digginroom.model.mapper.SongMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song

object RoomMapper {
    fun RoomModel.toDomain(): Room {
        return Room(
            videoId,
            song.toDomain(),
            isScrapped,
            roomId,
        )
    }

    fun Room.toModel(): RoomModel {
        return RoomModel(
            videoId,
            song.toModel(),
            isScrapped,
            roomId,
        )
    }

    fun RoomResponse.toDomain(): Room {
        return Room(
            videoId,
            Song(
                "",
                "",
                "",
                emptyList(),
                emptyList(),
            ),
            isScrapped,
            roomId,
        )
    }
}
