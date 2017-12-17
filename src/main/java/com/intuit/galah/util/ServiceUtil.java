package com.intuit.galah.util;

import java.util.LinkedHashSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.intuit.galah.model.Fault;
import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;

@Component
public class ServiceUtil {

	public boolean isValidUser(User user) {
		if(user!=null) return true;
		else return false;
	}

	public boolean isValidPost(Post post) {
		if(post!=null) return true;
		else return false;
	}

	public boolean areValidUsers(LinkedHashSet<User> users) {
		if(users != null && !users.isEmpty()) return true;
		else return false;
	}

	public boolean areValidPosts(LinkedHashSet<Post> posts) {
		if(posts != null && !posts.isEmpty()) return true;
		else return false;
	}

	public ResponseEntity<Object> createResponseEntity(Object response,HttpStatus status) {
		if(status.is4xxClientError()) {
			Fault fault = createFault((String) response,status.value());
			return new ResponseEntity<>(fault,status);
		}
		return new ResponseEntity<>(response, status);
	}

	private Fault createFault(String response, int value) {
		return new Fault(value,response);
	}

}
