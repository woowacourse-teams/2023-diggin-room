package com.digginroom.digginroom.domain.track;

import com.digginroom.digginroom.domain.room.Genre;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "artist"}))
public class Track {

    private String title;
    private String artist;
    @Enumerated(value = EnumType.STRING)
    private Genre superGenre;
    @Convert(converter = SubGenreConverter.class)
    private List<String> subGenres;
    @Column(length = 500)
    private String description;

    @Builder
    public Track(
            final String title,
            final String artist,
            final Genre superGenre,
            final List<String> subGenres,
            final String description
    ) {
        this.title = title;
        this.artist = artist;
        this.superGenre = superGenre;
        this.subGenres = subGenres;
        this.description = description;
    }
}
