package com.digginroom.digginroom.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
    @Enumerated(value = EnumType.STRING)
    private Genre superGenre;
    @Convert(converter = SubGenreConverter.class)
    private List<String> subGenres;
    @Lob
    private String description;
    @OneToMany(mappedBy = "track")
    private final List<Room> rooms = new ArrayList<>();

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

    public Room recommendRoom() {
        int pickedIndex = ThreadLocalRandom.current().nextInt(rooms.size());
        return rooms.get(pickedIndex);
    }
}
