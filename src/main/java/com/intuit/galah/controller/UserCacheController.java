package com.intuit.galah.controller;

import java.util.LinkedHashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.iface.IUserService;
import com.intuit.galah.model.User;
import com.intuit.galah.util.ServiceUtil;

@RestController
public class UserCacheController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ServiceUtil serviceUtil;

	@GetMapping(value="/user/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUser(@PathVariable  String userId) {
		User user = userService.getUser(userId);
		if(serviceUtil.isValidUser(user))
			return serviceUtil.createResponseEntity(user,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("User not found in system",HttpStatus.NOT_FOUND);
	}

	@GetMapping(value="/user", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAllUsers() {
		LinkedHashSet<User> users = userService.getAllUsers();
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("No users in system",HttpStatus.NOT_FOUND);
	}

	@PostMapping(value="/user", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
		return new ResponseEntity<User>(userService.createUser(user),HttpStatus.OK);
	}

	@PutMapping(value="/user/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
		return new ResponseEntity<User>(userService.updateUser(user),HttpStatus.OK);
	}

	@DeleteMapping(value="/user/{userId}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> deleteUser(@PathVariable  String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<String>("Successfully deleted User::"+userId,HttpStatus.OK);
	}

}
