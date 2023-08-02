package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.SongModel
import com.digginroom.digginroom.model.mapper.GenreMapper.toDomain
import com.digginroom.digginroom.model.mapper.GenreMapper.toModel
import com.digginroom.digginroom.model.room.Song

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
