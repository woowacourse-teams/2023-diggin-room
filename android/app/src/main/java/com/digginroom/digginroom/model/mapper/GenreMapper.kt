package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.GenreModel
import com.digginroom.digginroom.model.room.genre.Genre

object GenreMapper {

    fun GenreModel.toDomain(): Genre {
        return Genre.find(title)
    }

    fun Genre.toModel(): GenreModel {
        return GenreModel(
            title
        )
    }
}
