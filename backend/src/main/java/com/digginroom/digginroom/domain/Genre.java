package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.GenreException.GenreNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {

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

    private final String name;

    public static Genre of(final String name) {
        return Arrays.stream(values())
                .filter(genre -> genre.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new GenreNotFoundException(name));
    }
}
