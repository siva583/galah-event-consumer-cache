package com.intuit.galah.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.Follow;
import com.intuit.galah.model.User;
import com.intuit.redis.service.PostService;
import com.intuit.redis.service.UserService;

@RestController
public class FollowRelationCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@PostMapping(value="/user/{userId}/follower", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollower(@RequestBody @Valid Follow follow) {
		 userService.addFollower(follow.getUserId(), follow.getDate(), follow.getfId());
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@DeleteMapping(value="/user/{userId}/follower", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> deleteFollower(@RequestBody @Valid Follow follow) {
		 userService.deleteFollower(follow.getUserId(), follow.getfId());
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@PostMapping(value="/user/{userId}/followerReq", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollowerRequests(@RequestBody Follow follow) {
		System.out.println("Inside controller::");
		 userService.addRequestToFollow(follow.getUserId(), follow.getDate(), follow.getfId());
		 return new ResponseEntity<String>("User request to follow "+follow.getfId()+" is submitted.",HttpStatus.OK);
	}
	
	@GetMapping(value="/user/{userId}/following", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Set<User> getUserFollowing(@PathVariable @Valid String userId) {
		return userService.getFollowingForUser(userId);
	}
	
	@GetMapping(value="/user/{userId}/followers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Set<User> getUserFollowers(@PathVariable @Valid String userId) {
		return userService.getFollowersForUser(userId);
	}
	
	@GetMapping(value="/user/{userId}/followerReqs", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Set<User> getUserFollowerReq(@PathVariable @Valid String userId) {
		return userService.getFollowingReqsForUser(userId);
	}
	

}
