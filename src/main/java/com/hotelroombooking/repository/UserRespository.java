package com.hotelroombooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotelroombooking.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {

	@Query(value = "select u from User u where u.name=:userName")
	Optional<User> findUserByName(String userName);

}
