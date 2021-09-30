package com.hotelroombooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelroombooking.model.Room;
import com.hotelroombooking.repository.RoomRespository;

@Service
public class RoomService {

	@Autowired
	RoomRespository roomRespository;

	public Room addRoom(Room room) {
		return roomRespository.save(room);
	}

	public Room addRoomWithUser(Room room) {
		room.setRoomAvailableStatus("not_available");
		return roomRespository.save(room);
	}

	public Optional<Room> findRoomById(int id) {
		return roomRespository.findById(id);
	}

	public Optional<Room> findRoomByRoomNumber(String roomNumber) {
		return roomRespository.findRoomByRoomNumber(roomNumber);
	}

	public List<Room> viewAllRoomsInHotel(String hotelName) {
		return roomRespository.findAllByHotelName(hotelName);
	}

	public List<Room> viewAllRooms() {
		return roomRespository.findAll();
	}

	public void removeRoomById(int id) {
		roomRespository.deleteById(id);
	}

	public void removeAllRoomsByHotelName(String hotelName) {
		List<Room> roomsToBeDeleted = roomRespository.findAllByHotelName(hotelName);
		roomRespository.deleteAll(roomsToBeDeleted);
	}

	public void updateRoom(int id, Room room) {
		Optional<Room> roomToUpdate = findRoomById(id);
		Room tempRoom = roomToUpdate.get();
		tempRoom.setHotelName(room.getHotelName());
		tempRoom.setPrice(room.getPrice());
		tempRoom.setRoomAvailableStatus(room.getRoomAvailableStatus());
		tempRoom.setRoomNumber(room.getRoomNumber());
		roomRespository.save(tempRoom);
	}

	public Optional<Room> updateRoomByHotelNameAndRoomNumber(String hotelName, String roomNumber, Room room) {
		Optional<Room> roomToUpdate = findRoomByHotelNameAndRoomNumber(hotelName, roomNumber);
		if (roomToUpdate.isPresent()) {
			Room tempRoom = roomToUpdate.get();
			tempRoom.setHotelName(room.getHotelName());
			tempRoom.setPrice(room.getPrice());
			tempRoom.setRoomAvailableStatus(room.getRoomAvailableStatus());
			tempRoom.setRoomNumber(room.getRoomNumber());
			roomRespository.save(tempRoom);
			return roomToUpdate;
		}
		return roomToUpdate;

	}

	public Optional<Room> findRoomByHotelNameAndRoomNumber(String hotelName, String roomNumber) {
		return roomRespository.findRoomByHotelNameAndRoomNumber(hotelName, roomNumber);
	}

	public List<Room> viewAllAvailableRooms() {
		return roomRespository.viewAllAvailableRooms("available");
	}
}
