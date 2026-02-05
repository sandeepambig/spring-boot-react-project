package com.hotel.booking.api.services;

import com.hotel.booking.api.entities.BookedRoom;
import com.hotel.booking.api.entities.Room;
import com.hotel.booking.api.exception.InvalidBookingRequestException;
import com.hotel.booking.api.exception.ResourceNotFoundException;
import com.hotel.booking.api.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final IRoomService roomService;


    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {

        return bookingRepository.findByRoomId(roomId) ;
    }

    @Override
    public void cancelBooking(Long bookingId) {

        bookingRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {

        System.out.println(bookingRequest);
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){

            throw new InvalidBookingRequestException("check-in date must come before check out date");
        }
        Room room = roomService.getRoomById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not found"));
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);

        if(roomIsAvailable){
            bookingRequest.calculateTotalNumberOfGuest();
            System.out.println(bookingRequest);
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }
        else {
            throw new InvalidBookingRequestException("Sorry this room is not available for the selected date");
        }
        return bookingRequest.getBookingConfirmationCode();
    }



    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {

        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookedRoom> getAllBookings() {

        return bookingRepository.findAll();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {

        return  existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
