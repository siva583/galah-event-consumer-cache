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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.iface.IPostService;
import com.intuit.galah.iface.IUserService;
import com.intuit.galah.model.Post;
import com.intuit.galah.util.ServiceUtil;

@RestController
public class PostCacheController {
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(PostCacheController.class);
	
	@GetMapping(value="/post/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getPost(@PathVariable @Valid String postId) {
		Post post = postService.getPost(postId);
		if(serviceUtil.isValidPost(post))
			return serviceUtil.createResponseEntity(post,HttpStatus.OK);
		else {
			LOG.debug("Post not found"+postId);
			return serviceUtil.createResponseEntity("Post not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/posts/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserPosts(@PathVariable  String userId) {
		LinkedHashSet<Post> posts = postService.getUserPosts(userId);
		if(serviceUtil.areValidPosts(posts))
			return serviceUtil.createResponseEntity(postService.getUserPosts(userId),HttpStatus.OK);
		else {
			LOG.debug("User did not post any messages yet"+userId);
			return serviceUtil.createResponseEntity("User did not post any messages yet",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/post", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAllPosts() {
		LinkedHashSet<Post> posts = postService.getAllPosts();
		if(serviceUtil.areValidPosts(posts))
			return serviceUtil.createResponseEntity(posts, HttpStatus.OK);
		else {
			LOG.debug("No posts found in system");
			return serviceUtil.createResponseEntity("No posts found in system", HttpStatus.NOT_FOUND); 
		}
	}
	
	@PostMapping(value="/post", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) {
		return new ResponseEntity<>(postService.createPost(post),HttpStatus.OK);
	}
	
	@PutMapping(value="/post/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Post> updatePost(@RequestBody @Valid Post post) {
		return new ResponseEntity<>(postService.updatePost(post),HttpStatus.OK);
	}

}
