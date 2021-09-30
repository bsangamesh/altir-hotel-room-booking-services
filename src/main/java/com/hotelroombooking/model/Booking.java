package com.hotelroombooking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotelbooking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String userName;

	private String userMobileNumber;

	private String userEmail;

	private String hotelName;

	private String roomNumber;

	private int priceOfRoom;

	@Temporal(TemporalType.DATE)
	private Date dateOfRoomBooking;

	public Booking(String userName, String userMobileNumber, String userEmail, String hotelName, String roomNumber,
			int priceOfRoom, Date dateOfRoomBooking) {
		this.userName = userName;
		this.userMobileNumber = userMobileNumber;
		this.userEmail = userEmail;
		this.hotelName = hotelName;
		this.roomNumber = roomNumber;
		this.priceOfRoom = priceOfRoom;
		this.dateOfRoomBooking = dateOfRoomBooking;
	}

	@Override
	public String toString() {
		return "Booking [userName=" + userName + ", userMobileNumber=" + userMobileNumber + ", userEmail=" + userEmail
				+ ", hotelName=" + hotelName + ", roomNumber=" + roomNumber + ", priceOfRoom=" + priceOfRoom
				+ ", dateOfRoomBooking=" + dateOfRoomBooking + "]";
	}

}
