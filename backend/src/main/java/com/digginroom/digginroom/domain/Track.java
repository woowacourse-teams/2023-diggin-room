package com.digginroom.digginroom.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "artist"}))
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String artist;
    private String superGenre;
    @Convert(converter = SubGenreConverter.class)
    private List<String> subGenres;

    @Builder
    public Track(final String title, final String artist, final String superGenre, final List<String> subGenres) {
        this.title = title;
        this.artist = artist;
        this.superGenre = superGenre;
        this.subGenres = subGenres;
    }
}
