package com.hotel.booking.api.controller;

import com.hotel.booking.api.entities.Room;
import com.hotel.booking.api.response.RoomResponse;
import com.hotel.booking.api.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addRoom(@RequestParam("photo") MultipartFile photo,
                                                @RequestParam("roomType") String roomType,
                                                @RequestParam("roomPrice")BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addRoom(photo,roomType,roomPrice);
        RoomResponse roomResponse = new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(),
                                                     savedRoom.getRoomPrice());

        return ResponseEntity.ok(roomResponse);
    }
}
