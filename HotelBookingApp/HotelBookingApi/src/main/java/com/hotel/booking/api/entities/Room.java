package com.hotel.booking.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked =false ;

    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookedRoom> bookings ;

    public Room(){
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom booking){

        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = UUID.randomUUID().toString();
        booking.setBookingConfirmationCode(bookingCode);
    }
}
