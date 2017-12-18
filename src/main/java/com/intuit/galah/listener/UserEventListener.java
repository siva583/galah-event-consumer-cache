package com.intuit.galah.listener;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.galah.iface.IUserService;
import com.intuit.galah.model.Follow;
import com.intuit.galah.model.User;

@Service
public class UserEventListener {

	private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

	private CountDownLatch createLatch = new CountDownLatch(1);
	private CountDownLatch updateLatch = new CountDownLatch(1);
	private CountDownLatch deleteLatch = new CountDownLatch(1);
	private CountDownLatch followReqLatch = new CountDownLatch(1);
	private CountDownLatch followAcceptLatch = new CountDownLatch(1);
	private CountDownLatch unfllowLatch = new CountDownLatch(1);


	@Autowired
	IUserService userService;


	@KafkaListener(topics = "${user.create.topic.name}")
	public void consumeUserCreateEvent(String payload) {
		User user = unMarshalToUserObject(payload);
		userService.createUser(user);
		createLatch.countDown();
	}

	@KafkaListener(topics = "${user.update.topic.name}")
	public void consumeUserUpdateEvent(String payload) {
		User user = unMarshalToUserObject(payload);
		userService.updateUser(user);
		updateLatch.countDown();
	}

	@KafkaListener(topics = "${user.delete.topic.name}")
	public void consumeUserDeleteEvent(String userId) {
		userService.deleteUser(userId);
		deleteLatch.countDown();
	}

	@KafkaListener(topics = "${user.follow.request.topic.name}")
	public void consumeUserFollowRequestEvent(String payload) {
		Follow follow = unMarshalToFollowObject(payload);
		userService.addRequestToFollow(follow.getUserId(), follow.getDate(), follow.getfId());
		followReqLatch.countDown();
	}



	@KafkaListener(topics = "${user.follow.accept.topic.name}")
	public void consumeUserFollowAcceptEvent(String payload) {
		Follow follow = unMarshalToFollowObject(payload);
		userService.addFollower(follow.getUserId(), follow.getDate(), follow.getfId());
		followAcceptLatch.countDown();
	}

	@KafkaListener(topics = "${user.unfollow.topic.name}")
	public void consumeUserUnFollowEvent(String payload) {
		Follow follow = unMarshalToFollowObject(payload);
		userService.deleteFollower(follow.getUserId(), follow.getfId());
		unfllowLatch.countDown();
	}

	private User unMarshalToUserObject(String payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(payload, User.class);
		} catch (IOException e) {
			logger.error("Error is:" + e.getStackTrace());
		}
		return user;
	}

	private Follow unMarshalToFollowObject(String payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		Follow follow=  null;
		try {
			follow = objectMapper.readValue(payload, Follow.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(" Error is:" + e.getStackTrace());
		}
		return follow;
	}

}
