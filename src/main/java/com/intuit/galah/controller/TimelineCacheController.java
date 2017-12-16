package com.intuit.galah.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.*;
import com.intuit.redis.service.PostService;
import com.intuit.redis.service.UserService;

@RestController
public class TimelineCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@GetMapping(value="/user/{userId}/feed", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Set<Post> getUser(@PathVariable @Valid String userId) {
		return userService.getUserTimeline(userId);
	}
}
