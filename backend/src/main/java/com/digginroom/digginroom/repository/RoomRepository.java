package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
