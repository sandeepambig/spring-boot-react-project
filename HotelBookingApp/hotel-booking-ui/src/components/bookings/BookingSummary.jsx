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
                    <strong>Adult{booking.numebrOfAdults > 1 ? "s": ""}: {booking.numberOfAdults}</strong>
                    <strong>Children{booking.numberOfChildren > 1 ? "s": ""}: {booking.numberOfChildren}</strong>
                
                </div>
               
     </div> 
     
)
}
export default BookingSummary