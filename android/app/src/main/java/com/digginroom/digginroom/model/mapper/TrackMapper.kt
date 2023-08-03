package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.TrackResponse
import com.digginroom.digginroom.model.TrackModel
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Track

object TrackMapper {

    fun TrackModel.toDomain(): Track {
        return Track(
            title,
            artist,
            Genre(superGenre)
        )
    }

    fun Track.toModel(): TrackModel {
        return TrackModel(
            title,
            artist,
            superGenre.title
        )
    }

    fun TrackResponse.toDomain(): Track {
        return Track(
            title,
            artist,
            Genre(superGenre)
        )
    }
}