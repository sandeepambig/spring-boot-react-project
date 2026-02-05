import React, { useState, useEffect } from "react"
import moment from "moment"
import Button from "react-bootstrap/Button"
import { useNavigate } from "react-router-dom"

const BookingSummary = ({ booking, payment, isFormValid, onConfirm }) => {
	const checkInDate = moment(booking.checkInDate)
	const checkOutDate = moment(booking.checkOutDate)
	const numberOfDays = checkOutDate.diff(checkInDate, "days")
	const [isBookingConfirmed, setIsBookingConfirmed] = useState(false)
	const [isProcessingPayment, setIsProcessingPayment] = useState(false)
	const navigate = useNavigate()

	const handleConfirmBooking = () => {
		setIsProcessingPayment(true)
		setTimeout(() => {
			setIsProcessingPayment(false)
			setIsBookingConfirmed(true)
			onConfirm()
		}, 3000)
	}

	useEffect(() => {
		if (isBookingConfirmed) {
			navigate("/booking-success")
		}
	}, [isBookingConfirmed, navigate])

	return (
		<div className="row">
			
				<h4 className="card-title hotel-color">Reservation Summary</h4>
                <p>Payment: {payment}</p>
				<p>
					Name: <strong>{booking.guestFullName}</strong>
				</p>
				<p>
					Email: <strong>{booking.guestEmail}</strong>
				</p>
				<p>
					Check-in Date: <strong>{moment(booking.checkInDate).format("MMM Do YYYY")}</strong>
				</p>
				<p>
					Check-out Date: <strong>{moment(booking.checkOutDate).format("MMM Do YYYY")}</strong>
				</p>
				<p>
					Number of Days Booked: <strong>{numberOfDays}</strong>
				</p>
                <div>
                    <h5>Number Guests</h5>
                    <strong>Adult{booking.numOfAdults > 1 ? "s": ""}: {booking.numOfAdults}</strong>
                    <strong>Children{booking.numOfChildren > 1 ? "s": ""}: {booking.numOfChildren}</strong>
                    
                </div>
                {
                   payment > 0 ? (
                    <>
                    <p>Total Payment : <strong>${payment}</strong></p>

                    {
                        isFormValid && !isBookingConfirmed ? (
                            <Button variant="success" onClick={handleConfirmBooking}>
                              {isProcessingPayment ? (
                                <>
                                  <span className="spinner-borderspinner-border-sm mr-2"
                                        role="status"
                                        aria-hidden="true"></span>
                                  Booking confirmed , redirecting to payment ...
                                </>
                              ) :(
                                "Confirm Booking and proceed to payment"
                              )}
                            </Button>
                        
                       ) : isBookingConfirmed ?(
                            
                        <div className="d-flex justify-content-center align-items-center">
                            <div className="spinner-border text-primary">
                                <span className="sr-only">Loading</span>
                            </div>
                        </div>

                       ) : null
                    }
                    </>
                  ) : ( 
                      <p className="text-danger">Checkout-out-date must be after check-in-date</p>
                  )
                }
               
     </div> 
     
)
}
export default BookingSummary