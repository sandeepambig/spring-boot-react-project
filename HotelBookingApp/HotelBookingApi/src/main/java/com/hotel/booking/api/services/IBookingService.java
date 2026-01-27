package com.hotel.booking.api.services;

import com.hotel.booking.api.entities.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);
}
