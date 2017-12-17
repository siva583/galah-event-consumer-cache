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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.Follow;
import com.intuit.galah.model.User;
import com.intuit.galah.util.ServiceUtil;
import com.intuit.redis.service.PostService;
import com.intuit.redis.service.UserService;

@RestController
public class FollowRelationCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@PostMapping(value="/follower/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollower(@RequestBody @Valid Follow follow) {
		 userService.addFollower(follow.getUserId(), follow.getDate(), follow.getfId());
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@DeleteMapping(value="/follower/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> deleteFollower(@RequestBody @Valid Follow follow) {
		 userService.deleteFollower(follow.getUserId(), follow.getfId());
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@PostMapping(value="/followerReq/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollowerRequests(@RequestBody Follow follow) {
		 userService.addRequestToFollow(follow.getUserId(), follow.getDate(), follow.getfId());
		 return new ResponseEntity<String>("User request to follow "+follow.getfId()+" is submitted.",HttpStatus.OK);
	}
	
	@GetMapping(value="/following/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowing(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users = userService.getFollowingForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("No following for this user",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/followers/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowers(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users =  userService.getFollowersForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("No followers for this user",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/followerReqs/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowerReq(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users =  userService.getFollowingReqsForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("No open following requests for this user",HttpStatus.NOT_FOUND);
	}
	

}
