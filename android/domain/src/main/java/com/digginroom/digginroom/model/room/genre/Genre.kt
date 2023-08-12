package com.digginroom.digginroom.model.room.genre

enum class Genre(val title: String) {

    AMBIENT("Ambient"),
    BLUES("Blues"),
    CLASSICAL_MUSIC("Classical Music"),
    COMEDY("Comedy"),
    COUNTRY("Country"),
    DANCE("Dance"),
    ELECTRONIC("Electronic"),
    EXPERIMENTAL("Experimental"),
    FIELD_RECORDINGS("Field Recordings"),
    FOLK("Folk"),
    HIP_HOP("Hip Hop"),
    INDUSTRIAL_MUSIC("Industrial Music"),
    JAZZ("Jazz"),
    METAL("Metal"),
    MUSICAL_THEATRE_AND_ENTERTAINMENT("Musical Theatre and Entertainment"),
    NEW_AGE("New Age"),
    POP("Pop"),
    PSYCHEDELIA("Psychedelia"),
    PUNK("Punk"),
    RNB("R&B"),
    REGIONAL_MUSIC("Regional Music"),
    ROCK("Rock"),
    SINGER_SONGWRITER("Singer-Songwriter"),
    SKA("Ska"),
    SOUNDS_AND_EFFECTS("Sounds and Effects"),
    SPOKEN_WORD("Spoken Word");

    companion object {

        private const val NO_SUCH_GENRE = "해당 장르는 존재하지 않습니다."

        fun find(title: String): Genre {
            return values().find { it.title == title }
                ?: throw NoSuchElementException(NO_SUCH_GENRE)
        }
    }
}
