package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.Track;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findBySuperGenre(final Genre superGenre);
}
