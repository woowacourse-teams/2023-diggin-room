package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.R
import com.digginroom.digginroom.model.GenreTasteModel
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.genre.GenreTaste

object GenreTasteMapper {

    fun GenreTaste.toModel(): GenreTasteModel = when (this.genre) {
        Genre.AMBIENT -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_ambient,
            isSelected
        )

        Genre.BLUES -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_blues,
            isSelected
        )

        Genre.COUNTRY -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_country,
            isSelected
        )

        Genre.CLASSICAL_MUSIC -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_classical_music,
            isSelected
        )

        Genre.DANCE -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_dance,
            isSelected
        )

        Genre.ELECTRONIC -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_electronic,
            isSelected
        )

        Genre.EXPERIMENTAL -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_experimental,
            isSelected
        )

        Genre.FOLK -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_folk,
            isSelected
        )

        Genre.HIP_HOP -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_hip_hop,
            isSelected
        )

        Genre.INDUSTRIAL_MUSIC -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_industrial_music,
            isSelected
        )

        Genre.JAZZ -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_jazz,
            isSelected
        )

        Genre.METAL -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_metal,
            isSelected
        )

        Genre.MUSICAL_THEATRE_AND_ENTERTAINMENT -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_musical_theatre,
            isSelected
        )

        Genre.NEW_AGE -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_new_age,
            isSelected
        )

        Genre.POP -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_pop,
            isSelected
        )

        Genre.PSYCHEDELIA -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_psychedelia,
            isSelected
        )

        Genre.PUNK -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_punk,
            isSelected
        )

        Genre.RNB -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_rb,
            isSelected
        )

        Genre.ROCK -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_rock,
            isSelected
        )

        Genre.SINGER_SONGWRITER -> GenreTasteModel(
            genre.title,
            R.drawable.ic_genre_singer_songwriter,
            isSelected
        )
    }

    fun GenreTasteModel.toDomain(): GenreTaste =
        GenreTaste(
            genre = Genre.find(title),
            isSelected = isSelected
        )
}
