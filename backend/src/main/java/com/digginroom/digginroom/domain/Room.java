package com.digginroom.digginroom.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST)
    private List<MediaSource> mediaSources;

    public Room(final List<MediaSource> mediaSources) {
        validateNotEmpty(mediaSources);
        this.mediaSources = mediaSources;
    }

    private void validateNotEmpty(final List<MediaSource> mediaSources) {
        if (mediaSources.size() == 0) {
            throw new IllegalArgumentException("룸의 미디어 소스는 비어있을 수 없습니다");
        }
    }
}
