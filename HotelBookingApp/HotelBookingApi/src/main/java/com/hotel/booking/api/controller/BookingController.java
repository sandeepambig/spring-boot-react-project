package com.hotel.booking.api.controller;

import com.hotel.booking.api.entities.BookedRoom;
import com.hotel.booking.api.entities.Room;
import com.hotel.booking.api.exception.InvalidBookingRequestException;
import com.hotel.booking.api.exception.ResourceNotFoundException;
import com.hotel.booking.api.response.BookingResponse;
import com.hotel.booking.api.response.RoomResponse;
import com.hotel.booking.api.services.IBookingService;
import com.hotel.booking.api.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings (){

        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom room: bookings){
            BookingResponse bookingResponse = getBookingResponse(room);
            bookingResponses.add(bookingResponse);
        }

        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingConfirmationCode(@PathVariable String confirmationCode){

        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,@RequestBody BookedRoom bookingRequest){

        try{
            String confirmationCode = bookingService.saveBooking(roomId , bookingRequest);
            return ResponseEntity.ok("Room Booked successfully ! Your booking confirmation code is : "+ confirmationCode);
        } catch (InvalidBookingRequestException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){

        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking){

        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),
                                             theRoom.getRoomType(),
                                             theRoom.getRoomPrice());
        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(),
                                   booking.getCheckOutDate(),booking.getGuestFullName(),
                                   booking.getGuestEmail(),booking.getNumOfAdults(),
                                   booking.getNumOfChildren(),booking.getTotalNumberOfGuest(),
                                   booking.getBookingConfirmationCode(), room);

    }
}
