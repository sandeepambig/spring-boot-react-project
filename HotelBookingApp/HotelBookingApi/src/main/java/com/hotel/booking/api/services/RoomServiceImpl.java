package com.hotel.booking.api.services;

import com.hotel.booking.api.entities.Room;
import com.hotel.booking.api.exception.InternalServerException;
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
import java.util.Optional;

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

    @Override
    public void deleteRoom(Long roomId) {

       Optional<Room> room = roomRepository.findById(roomId);
       if(room.isPresent()) {
           roomRepository.deleteById(roomId);
       }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {

       Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room not Found"));

       if(roomType != null) room.setRoomType(roomType);
       if (roomPrice != null) room.setRoomPrice(roomPrice);
       try {
           if (photoBytes != null && photoBytes.length > 0) {
               room.setPhoto(new SerialBlob(photoBytes));
           }
       } catch(SQLException e){
             throw new InternalServerException("Error updating room");
       }

        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {


        return Optional.of(roomRepository.findById(roomId).get());
    }


}
