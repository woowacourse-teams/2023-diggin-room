package com.digginroom.digginroom.domain;

import java.util.ArrayList;
import java.util.List;

import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @ManyToMany
    private final List<Room> scraps = new ArrayList<>();

    public void scrap(final Room room) {
        validateUnscrapped(room);
        scraps.add(room);
    }

    public void unscrap(final Room room) {
        validateScrapped(room);
        scraps.remove(room);
    }

    public boolean hasScrapped(final Room room) {
        return scraps.contains(room);
    }

    private void validateUnscrapped(Room room) {
        if (hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    private void validateScrapped(Room room) {
        if (!hasScrapped(room)) {
            throw new NotScrappedException();
        }
    }
}
