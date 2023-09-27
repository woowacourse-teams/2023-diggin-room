package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByTrackSuperGenre(final Genre superGenre);
}
