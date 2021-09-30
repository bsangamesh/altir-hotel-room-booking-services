package com.hotelroombooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelroombooking.model.Booking;
import com.hotelroombooking.model.Room;
import com.hotelroombooking.model.User;
import com.hotelroombooking.service.BookingService;
import com.hotelroombooking.service.RoomService;
import com.hotelroombooking.service.UserService;

@RestController
@RequestMapping("altir/admin")
public class AdminController {

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;

	@Autowired
	private UserService userService;

	@GetMapping("/rooms")
	public ResponseEntity<?> viewAllRooms() {
		List<Room> allRooms = roomService.viewAllRooms();

		if (allRooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Room>>(allRooms, HttpStatus.OK);
	}

	@GetMapping("/rooms/{hotelName}")
	public ResponseEntity<?> viewAllRoomsInHotel(@PathVariable("hotelName") String hotelName) {
		List<Room> allRooms = roomService.viewAllRoomsInHotel(hotelName);

		if (allRooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Room>>(allRooms, HttpStatus.OK);
	}

	@PostMapping("/rooms")
	public ResponseEntity<String> insertRoom(@RequestBody Room room) {
		if (room == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		} else if (room.getHotelName() == null || room.getRoomNumber() == null || room.getPrice() == 0
				|| Integer.valueOf(room.getPrice()) == null || room.getRoomAvailableStatus() == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		}

		Optional<Room> roomFinder = roomService.findRoomByRoomNumber(room.getRoomNumber());

		if (roomFinder.isPresent()) {
			return new ResponseEntity<String>(
					"Room No " + roomFinder.get().getRoomNumber() + " is already present in "
							+ roomFinder.get().getHotelName() + " - Hotel!, so please add other Room number",
					HttpStatus.OK);
		}

		Room addedRoom = roomService.addRoom(room);
		return new ResponseEntity<String>("Room No " + addedRoom.getRoomNumber() + " added to "
				+ addedRoom.getHotelName() + " Hotel Successfully!", HttpStatus.OK);
	}

	@DeleteMapping("/rooms/{hotelName}")
	public ResponseEntity<String> deleteRoomsFromHotel(@PathVariable String hotelName) {
		List<Room> allRooms = roomService.viewAllRoomsInHotel(hotelName);

		if (allRooms == null || allRooms.isEmpty()) {
			return new ResponseEntity<String>("No Rooms present in Hotel - " + hotelName + " to Delete.",
					HttpStatus.OK);
		}

		roomService.removeAllRoomsByHotelName(hotelName);

		return new ResponseEntity<String>("All the Rooms present in Hotel - " + hotelName + " are Deleted!!",
				HttpStatus.OK);
	}

	@PutMapping("/rooms/{id}")
	public ResponseEntity<String> UpdateRoomById(@PathVariable int id, @RequestBody Room room) {
		if (room == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details to Delete the Hotel",
					HttpStatus.BAD_REQUEST);
		} else if (room.getHotelName() == null || room.getRoomNumber() == null || room.getPrice() == 0
				|| Integer.valueOf(room.getPrice()) == null || room.getRoomAvailableStatus() == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details to Delete the Hotel",
					HttpStatus.BAD_REQUEST);
		}

		roomService.updateRoom(id, room);
		return new ResponseEntity<String>("Room Details are Updated!", HttpStatus.OK);

	}

	@PutMapping("/rooms/{hotelName}/{roomNumber}")
	public ResponseEntity<String> UpdateRoomByHotelNameAndRoomNumber(@PathVariable("hotelName") String hotelName,
			@PathVariable("roomNumber") String roomNumber, @RequestBody Room room) {

		if (room == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details to Delete the Hotel",
					HttpStatus.BAD_REQUEST);
		} else if (room.getHotelName() == null || room.getRoomNumber() == null || room.getPrice() == 0
				|| Integer.valueOf(room.getPrice()) == null || room.getRoomAvailableStatus() == null) {
			return new ResponseEntity<String>("Room details are not valid, Enter Valid Details to Delete the Hotel",
					HttpStatus.BAD_REQUEST);
		}

		Optional<Room> tempRoom = roomService.updateRoomByHotelNameAndRoomNumber(hotelName, roomNumber, room);
		if (tempRoom.isPresent()) {
			return new ResponseEntity<String>("Room Details are Updated!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Room Details Not Found!!!", HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/rooms/available")
	public ResponseEntity<List<Room>> availableRooms() {
		List<Room> allAvailableRooms = roomService.viewAllAvailableRooms();

		if (allAvailableRooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Room>>(allAvailableRooms, HttpStatus.OK);
	}

	@GetMapping("/rooms/booked")
	public ResponseEntity<?> viewAllBookedRooms() {
		List<Booking> bookedRooms = bookingService.viewAllBookedRooms();

		if (bookedRooms.isEmpty()) {
			return new ResponseEntity<String>("No Rooms are Booked Yet", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Booking>>(bookedRooms, HttpStatus.OK);
	}

	@GetMapping("/users/{userName}/booking/price")
	public ResponseEntity<?> viewTotalBookingPriceOfAUser(@PathVariable String userName) {
		Optional<User> tempUser = userService.findUserByName(userName);

		if (!tempUser.isPresent()) {
			return new ResponseEntity<String>("User Not Found!!", HttpStatus.NO_CONTENT);
		}

		int totalPrice = bookingService.getTotalBookingPriceOfAUser(userName);

		return new ResponseEntity<String>("User " + userName + " booked Hotel Rooms of price : " + totalPrice,
				HttpStatus.OK);
	}

	@GetMapping("/rooms/totalrevenue")
	public ResponseEntity<?> viewTotalRevenue() {

		int totalPrice = bookingService.getTotalRevenue();

		return new ResponseEntity<String>("Total Revenue is : " + totalPrice,
				HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> viewAllUsers() {
		List<User> allUsers = userService.findAllUsers();

		if (allUsers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

}
