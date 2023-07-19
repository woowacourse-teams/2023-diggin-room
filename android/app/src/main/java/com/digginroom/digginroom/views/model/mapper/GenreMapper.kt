package com.digginroom.digginroom.views.model.mapper

import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.views.model.GenreModel

object GenreMapper {

    fun GenreModel.toDomain(): Genre {
        return Genre(
            title
        )
    }

    fun Genre.toModel(): GenreModel {
        return GenreModel(
            title
        )
    }
}
