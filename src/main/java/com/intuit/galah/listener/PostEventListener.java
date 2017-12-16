/*package com.intuit.galah.listener;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.galah.model.Post;
import com.intuit.redis.service.PostService;

@Service
public class PostEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(PostEventListener.class);

	private CountDownLatch createLatch = new CountDownLatch(1);
	private CountDownLatch updateLatch = new CountDownLatch(1);
	private CountDownLatch deleteLatch = new CountDownLatch(1);
	
	@Autowired
	PostService postService;
	
	@KafkaListener(topics = "${post.create.topic.name}")
	public void consumePostCreateEvent(String payload) {
		Post post = unMarshalToPostObject(payload);
		postService.createPost(post);
		createLatch.countDown();

	}
	
	@KafkaListener(topics = "${post.update.topic.name}")
	public void consumePostUpdateEvent(String payload) {
		Post post = unMarshalToPostObject(payload);
		postService.updatePost(post);
		updateLatch.countDown();
	}
	
	@KafkaListener(topics = "${post.delete.topic.name}")
	public void consumeUserDeleteEvent(String postId) {
		postService.deletePost(postId);
		deleteLatch.countDown();
	}
	
	private Post unMarshalToPostObject(String payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		Post post = null;
		try {
			post = objectMapper.readValue(payload, Post.class);
		} catch (IOException e) {
			logger.error("Error is:" + e.getStackTrace());
		}
		return post;
	}
	
	

}
*/