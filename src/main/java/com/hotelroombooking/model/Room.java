package com.hotelroombooking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room")
//Added below line to not get Infinite loop when retriving user and room booked details from rest client
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "users" })
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String hotelName;

	private String roomNumber;

	private int price;

	private String roomAvailableStatus;

	//Single room can booked by multiple user in different days and once room is available
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "rooms")
	private List<User> users = new ArrayList<User>();

	public Room(String hotelName, String roomNumber, int price, String roomAvailableStatus) {
		this.hotelName = hotelName;
		this.roomNumber = roomNumber;
		this.price = price;
		this.roomAvailableStatus = roomAvailableStatus;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", hotelName=" + hotelName + ", roomNumber=" + roomNumber + ", price=" + price
				+ ", roomAvailableStatus=" + roomAvailableStatus + "]";
	}

}
