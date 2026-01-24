package com.hotel.booking.api.repositories;

import com.hotel.booking.api.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
