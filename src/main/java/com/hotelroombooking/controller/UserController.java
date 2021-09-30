package com.hotelroombooking.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hotelroombooking.model.Room;
import com.hotelroombooking.model.User;
import com.hotelroombooking.service.BookingService;
import com.hotelroombooking.service.RoomService;
import com.hotelroombooking.service.UserService;

@RestController
@RequestMapping("altir/users")
public class UserController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookingService bookingService;

	@GetMapping("/rooms")
	public ResponseEntity<?> viewAllRooms() {
		List<Room> allRooms = roomService.viewAllRooms();

		if (allRooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Room>>(allRooms, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public @ResponseBody String register(@RequestBody(required = false) User user) {

		if (user == null) {
			return "Enter Valid User Details - User details should not be Null";
		} else if (user.getName() == null || user.getEmail() == null || user.getMobileNumber() == null) {
			return "Enter Valid User Details - All the fields(Name, Password, Email) are mandatory";
		}
		userService.signUp(user);
		return user.getName() + " Signed Up Successfully!";
	}

	@PostMapping("/{userName}/rooms/{id}")
	public ResponseEntity<String> bookRoomById(@PathVariable String userName, @PathVariable int id) {
		Optional<Room> tempRoom = roomService.findRoomById(id);
		Optional<User> tempUser = userService.findUserByName(userName);

		if (!tempUser.isPresent()) {
			return new ResponseEntity<String>("User details not found with the given Name, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		}

		if (!tempRoom.isPresent()) {
			return new ResponseEntity<String>("Room details not found with the given ID, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		}
		if (tempRoom.isPresent()) {
			if (tempRoom.get().getRoomAvailableStatus().equalsIgnoreCase("available")) {
				Room room = tempRoom.get();
				User user = tempUser.get();

				user.addRoom(room);
				room.addUser(user);

				userService.addUserWithProduct(user);
				roomService.addRoomWithUser(room);

				bookingService.saveRoomBookingReport(user, room);

			} else {
				return new ResponseEntity<String>("Room is already Booked", HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>("Room is Booked Successfull!!", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/{userName}/rooms/{hotelName}/{roomNumber}")
	public ResponseEntity<String> bookSingleRoomForMultipleDays(@PathVariable String userName,
			@PathVariable String hotelName, @PathVariable String roomNumber, @RequestParam List<String> dates)
			throws ParseException {

		Optional<Room> tempRoom = roomService.findRoomByHotelNameAndRoomNumber(hotelName, roomNumber);
		Optional<User> tempUser = userService.findUserByName(userName);

		if (!tempUser.isPresent()) {
			return new ResponseEntity<String>("User details not found with the given Name, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		}

		if (!tempRoom.isPresent()) {
			return new ResponseEntity<String>("Room details not found with the given ID, Enter Valid Details",
					HttpStatus.BAD_REQUEST);
		}
		if (tempRoom.isPresent()) {
			if (tempRoom.get().getRoomAvailableStatus().equalsIgnoreCase("available")) {

				for (String date : dates) {
					Room room = tempRoom.get();
					User user = tempUser.get();

					user.addRoom(room);
					room.addUser(user);

					userService.addUserWithProduct(user);
					roomService.addRoomWithUser(room);

					bookingService.saveRoomBookingReportForDates(user, room, date);
				}

			} else {
				return new ResponseEntity<String>("Room is already Booked", HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>("Room is Booked Successfull!!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/rooms/available")
	public ResponseEntity<List<Room>> availableRooms() {
		List<Room> allAvailableRooms = roomService.viewAllAvailableRooms();

		if (allAvailableRooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Room>>(allAvailableRooms, HttpStatus.OK);
	}

}
