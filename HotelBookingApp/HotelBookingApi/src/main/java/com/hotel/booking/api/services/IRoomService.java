package com.hotel.booking.api.services;

import com.hotel.booking.api.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface IRoomService {
    Room addRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;
}
