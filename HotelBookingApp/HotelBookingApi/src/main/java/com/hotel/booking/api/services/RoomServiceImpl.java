package com.hotel.booking.api.services;

import com.hotel.booking.api.entities.Room;
import com.hotel.booking.api.exception.ResourceNotFoundException;
import com.hotel.booking.api.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import com.hotel.booking.api.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService{

    private final RoomRepository roomRepository;

    @Override
    public Room addRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {

        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!file.isEmpty()){
            byte[] photoByte = file.getBytes();
            Blob photoBlob = new SerialBlob(photoByte);
            room.setPhoto(photoBlob);
        }


        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {

       Room room= roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not Found by the given id"+ roomId));

       Blob photoBlob = room.getPhoto();

       if(photoBlob != null){
           return photoBlob.getBytes(1,(int)photoBlob.length());
       }

       return null;
    }


}
