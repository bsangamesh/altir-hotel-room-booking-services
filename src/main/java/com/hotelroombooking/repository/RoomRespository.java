package com.hotelroombooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotelroombooking.model.Room;

@Repository
public interface RoomRespository extends JpaRepository<Room, Integer> {

	@Query(value = "select r from Room r where r.hotelName=:hotelName")
	List<Room> findAllByHotelName(String hotelName);

	@Query(value = "delete from Room r where  r.hotelName=:hotelName")
	void deleteAllRoomsByHotelName(String hotelName);

	@Query(value = "select r from Room r where r.roomNumber=:roomNumber")
	Optional<Room> findRoomByRoomNumber(String roomNumber);

	@Query(value = "select r from Room r where r.roomNumber=:roomNumber and r.hotelName=:hotelName")
	Optional<Room> findRoomByHotelNameAndRoomNumber(String hotelName, String roomNumber);

	@Query(value = "select r from Room r where r.roomAvailableStatus=:status")
	List<Room> viewAllAvailableRooms(String status);

}
