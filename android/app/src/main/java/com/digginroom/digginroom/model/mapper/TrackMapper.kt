package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.TrackResponse
import com.digginroom.digginroom.model.TrackModel
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.model.room.genre.Genre

object TrackMapper {

    fun TrackModel.toDomain(): Track {
        return Track(
            title,
            artist,
            Genre.find(superGenre),
            description
        )
    }

    fun Track.toModel(): TrackModel {
        return TrackModel(
            title,
            artist,
            superGenre.title,
            description
        )
    }

    fun TrackResponse.toDomain(): Track {
        return Track(
            title,
            artist,
            Genre.find(superGenre),
            description
        )
    }
}
