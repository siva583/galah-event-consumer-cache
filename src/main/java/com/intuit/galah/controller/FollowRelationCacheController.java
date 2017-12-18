package com.intuit.galah.controller;

import java.util.LinkedHashSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.intuit.galah.config.CorsFilter;
import com.intuit.galah.iface.IPostService;
import com.intuit.galah.iface.IUserService;
import com.intuit.galah.model.Follow;
import com.intuit.galah.model.User;
import com.intuit.galah.util.ServiceUtil;

@RestController
public class FollowRelationCacheController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(FollowRelationCacheController.class);
	
	@PostMapping(value="/follower/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollower(@RequestBody @Valid Follow follow) {
		 userService.addFollower(follow.getUserId(), follow.getDate(), follow.getfId());
		 LOG.debug("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.");
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@DeleteMapping(value="/follower/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> deleteFollower(@RequestBody @Valid Follow follow) {
		 userService.deleteFollower(follow.getUserId(), follow.getfId());
		 LOG.debug("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.");
		 return new ResponseEntity<String>("User "+follow.getUserId()+" is following "+ follow.getfId() +" now.", HttpStatus.OK) ;
	}
	
	@PostMapping(value="/followerReq/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addFollowerRequests(@RequestBody Follow follow) {
		 userService.addRequestToFollow(follow.getUserId(), follow.getDate(), follow.getfId());
		 LOG.debug("User request to follow "+follow.getfId()+" is submitted.");
		 return new ResponseEntity<String>("User request to follow "+follow.getfId()+" is submitted.",HttpStatus.OK);
	}
	
	@GetMapping(value="/following/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowing(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users = userService.getFollowingForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else {
			 LOG.debug("No following for this user"+userId);
			return serviceUtil.createResponseEntity("No following for this user",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/followers/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowers(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users =  userService.getFollowersForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else {
			 LOG.debug("No followers for this user"+userId);
			return serviceUtil.createResponseEntity("No followers for this user",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/followerReqs/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFollowerReq(@PathVariable @Valid String userId) {
		LinkedHashSet<User> users =  userService.getFollowingReqsForUser(userId);
		if(serviceUtil.areValidUsers(users))
			return serviceUtil.createResponseEntity(users,HttpStatus.OK);
		else {
			 LOG.debug("No open following requests for this user"+userId);
			return serviceUtil.createResponseEntity("No open following requests for this user",HttpStatus.NOT_FOUND);
		}
	}
	

}
