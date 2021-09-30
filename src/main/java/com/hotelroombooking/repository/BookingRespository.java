package com.hotelroombooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotelroombooking.model.Booking;

@Repository
public interface BookingRespository extends JpaRepository<Booking, Integer> {

	@Query(value = "select priceOfRoom from Booking b where b.userName=:userName")
	List<Integer> findPriceOfBookingRoomsOf(String userName);

	@Query(value = "select priceOfRoom from Booking b")
	List<Integer> findPriceOfBookedRooms();

}
