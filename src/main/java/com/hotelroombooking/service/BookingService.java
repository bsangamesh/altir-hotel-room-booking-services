package com.hotelroombooking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelroombooking.model.Booking;
import com.hotelroombooking.model.Room;
import com.hotelroombooking.model.User;
import com.hotelroombooking.repository.BookingRespository;

@Service
public class BookingService {

	@Autowired
	BookingRespository bookingRespository;

	public void saveRoomBookingReport(User user, Room room) {
		Booking booking = new Booking(user.getName(), user.getMobileNumber(), user.getEmail(), room.getHotelName(),
				room.getRoomNumber(), room.getPrice(), new Date());
		bookingRespository.save(booking);
	}

	public void saveRoomBookingReportForDates(User user, Room room, String date) throws ParseException {
		Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		Booking booking = new Booking(user.getName(), user.getMobileNumber(), user.getEmail(), room.getHotelName(),
				room.getRoomNumber(), room.getPrice(), tempDate);
		bookingRespository.save(booking);
	}

	public List<Booking> viewAllBookedRooms() {
		return bookingRespository.findAll();
	}

	public int getTotalBookingPriceOfAUser(String userName) {
		List<Integer> prices = bookingRespository.findPriceOfBookingRoomsOf(userName);
		if (prices == null || prices.isEmpty()) {
			return 0;
		}
		int totalPrice = prices.stream().mapToInt(val -> val).sum();
		return totalPrice;
	}

	public int getTotalRevenue() {
		List<Integer> prices = bookingRespository.findPriceOfBookedRooms();
		if (prices == null || prices.isEmpty()) {
			return 0;
		}
		int totalPrice = prices.stream().mapToInt(val -> val).sum();
		return totalPrice;
	}

}
