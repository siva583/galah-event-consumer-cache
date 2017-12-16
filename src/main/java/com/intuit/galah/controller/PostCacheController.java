package com.intuit.galah.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;
import com.intuit.redis.service.PostService;
import com.intuit.redis.service.UserService;

@RestController
public class PostCacheController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@GetMapping(value="/post/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Post getPost(@PathVariable @Valid String postId) {
		return postService.getPost(postId);
	}
	
	@PostMapping(value="/post/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Post createPost(@RequestBody @Valid Post post) {
		return postService.createPost(post);
	}
	
	@PutMapping(value="/post/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Post updatePost(@RequestBody @Valid Post post) {
		return postService.updatePost(post);
	}

}
