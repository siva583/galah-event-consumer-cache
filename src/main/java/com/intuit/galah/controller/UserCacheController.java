package com.intuit.galah.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;
import com.intuit.redis.service.PostService;
import com.intuit.redis.service.UserService;

@RestController
public class UserCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@GetMapping(value="/user/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> getUser(@PathVariable  String userId) {
		return new ResponseEntity<User>(userService.getUser(userId),HttpStatus.OK);
	}
	
	@GetMapping(value="/user/{userId}/posts", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Set<Post>> getUserPosts(@PathVariable  String userId) {
		return new ResponseEntity<Set<Post>>(postService.getUserPosts(userId),HttpStatus.OK);
	}
	
	@GetMapping(value="/user", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
	}
		
	@PutMapping(value="/user/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> upsertUser(@RequestBody @Valid User user) {
		return new ResponseEntity<User>(userService.upsertUser(user),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/user/{userId}", produces = { MediaType.TEXT_PLAIN_VALUE }, consumes = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> deleteUser(@PathVariable  String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<String>("Successfully deleted User::"+userId,HttpStatus.OK);
	}
	
}
