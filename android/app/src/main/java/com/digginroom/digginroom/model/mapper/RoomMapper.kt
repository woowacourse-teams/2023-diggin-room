package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.RoomResponse
import com.digginroom.digginroom.data.entity.ScrappedRoomsResponse
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.ScrappedRoomModel
import com.digginroom.digginroom.model.mapper.TrackMapper.toDomain
import com.digginroom.digginroom.model.mapper.TrackMapper.toModel
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom

object RoomMapper {
    fun RoomModel.toDomain(): Room {
        return Room(
            videoId,
            isScrapped,
            track.toDomain(),
            roomId,
            scrapCount
        )
    }

    fun Room.toModel(): RoomModel {
        return RoomModel(
            videoId,
            isScrapped,
            track.toModel(),
            roomId,
            scrapCount
        )
    }

    fun ScrappedRoomModel.toDomain(): ScrappedRoom {
        return ScrappedRoom(
            room = room.toDomain(),
            isSelected = isSelected
        )
    }

    fun ScrappedRoom.toModel(): ScrappedRoomModel {
        return ScrappedRoomModel(
            room = room.toModel(),
            isSelected = isSelected
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

    fun ScrappedRoomsResponse.toDomain(): List<ScrappedRoom> {
        return scrappedRooms.map { it.toDomainScrap() }
    }

    private fun RoomResponse.toDomainScrap(): ScrappedRoom {
        return ScrappedRoom(
            room = Room(
                videoId,
                isScrapped,
                track.toDomain(),
                roomId,
                scrapCount
            ),
            isSelected = false
        )
    }
}
