package com.hotelroombooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelroombooking.model.User;
import com.hotelroombooking.repository.UserRespository;

@Service
public class UserService {

	@Autowired
	UserRespository userRespository;

	public void signUp(User user) {
		userRespository.save(user);

	}

	public void addUserWithProduct(User user) {
		userRespository.save(user);

	}

	public Optional<User> findUserByName(String userName) {
		return userRespository.findUserByName(userName);
	}

	public List<User> findAllUsers() {
		return userRespository.findAll();
	}

}
