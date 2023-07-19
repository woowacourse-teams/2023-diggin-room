package com.digginroom.digginroom.views.model.mapper

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.views.model.RoomModel
import com.digginroom.digginroom.views.model.mapper.SongMapper.toDomain
import com.digginroom.digginroom.views.model.mapper.SongMapper.toModel

object RoomMapper {
    fun RoomModel.toDomain(): Room {
        return Room(
            videoId,
            song.toDomain(),
            isScrapped
        )
    }

    fun Room.toModel(): RoomModel {
        return RoomModel(
            videoId,
            song.toModel(),
            isScrapped
        )
    }
}
