package com.intuit.galah.controller;

import java.util.LinkedHashSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.iface.IUserService;
import com.intuit.galah.model.Post;
import com.intuit.galah.util.ServiceUtil;

@RestController
public class TimelineCacheController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(TimelineCacheController.class);
	
	@GetMapping(value="/feed/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserFeed(@PathVariable @Valid String userId, @RequestParam Integer pageSize, @RequestParam Integer pageNumber) {
		LinkedHashSet<Post> posts = userService.getUserTimeline(userId,pageSize,pageNumber);
		if(serviceUtil.areValidPosts(posts))
			return serviceUtil.createResponseEntity(posts,HttpStatus.OK);
		else {
			LOG.debug("No posts found in this user timeline"+userId);
			return serviceUtil.createResponseEntity("No posts found in this user timeline",HttpStatus.OK);
		}
	}
}
