package com.hotel.booking.api.exception;

public class InvalidBookingRequestException extends RuntimeException {

    public InvalidBookingRequestException(String message){
        super(message);
    }
}
