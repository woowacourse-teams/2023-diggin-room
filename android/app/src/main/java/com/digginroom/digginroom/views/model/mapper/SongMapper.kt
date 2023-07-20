package com.digginroom.digginroom.views.model.mapper

import com.digginroom.digginroom.model.room.Song
import com.digginroom.digginroom.views.model.SongModel
import com.digginroom.digginroom.views.model.mapper.GenreMapper.toDomain
import com.digginroom.digginroom.views.model.mapper.GenreMapper.toModel

object SongMapper {

    fun SongModel.toDomain(): Song {
        return Song(
            title,
            albumTitle,
            artist,
            genres.map { it.toDomain() },
            tags
        )
    }

    fun Song.toModel(): SongModel {
        return SongModel(
            title,
            albumTitle,
            artist,
            genres.map { it.toModel() },
            tags
        )
    }
}
