package com.intuit.galah.controller;

import java.util.LinkedHashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.Post;
import com.intuit.galah.util.ServiceUtil;
import com.intuit.redis.service.UserService;

@RestController
@SuppressWarnings("rawtypes")
public class TimelineCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@GetMapping(value="/feed/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity getUserFeed(@PathVariable @Valid String userId) {
		LinkedHashSet<Post> posts = userService.getUserTimeline(userId);
		if(serviceUtil.areValidPosts(posts))
			return serviceUtil.createResponseEntity(posts,HttpStatus.OK);
		else
			return serviceUtil.createResponseEntity("No posts found in this user timeline",HttpStatus.OK);
	}
}
