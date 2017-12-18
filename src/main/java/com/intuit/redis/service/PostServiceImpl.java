package com.intuit.redis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.intuit.galah.constants.Constants;
import com.intuit.galah.iface.IPostService;
import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;

@Service
public class PostServiceImpl implements IPostService{

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> postHashOps;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> postListOps;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, Long> idOps;

	@Autowired
	UserServiceImpl userService;

	public Post createPost(Post post) {
		post.setPostId(String.valueOf(createNewPostId()));
		postHashOps.put(Constants.POST_KEY, post.getPostId(), post);
		postListOps.leftPush(Constants.POSTS_KEY+post.getUserId(), post.getPostId());
		Set<String> followers = userService.getFollwerIdsForUser(post.getUserId());
		followers.add(post.getUserId());
		for (String follower : followers) {
			postListOps.leftPush(Constants.TIMELINE_KEY + follower, post.getPostId());
		}
		return post;
	}
	
	public Post updatePost(Post post) {
		postHashOps.put(Constants.POST_KEY, post.getPostId(), post);
		return post;
	}

	public void deletePost(String postId) {

	}
	
	public Post getPost(String id) {
		return (Post) postHashOps.get(Constants.POST_KEY, id);
	}

	public LinkedHashSet<Post> getUserPosts(String userId) {
		List<String> postIds = postListOps.range(Constants.POSTS_KEY+userId, 0, -1);
		LinkedHashSet<Post> userPosts = new LinkedHashSet<Post>();
		for(String postId : postIds) {
			userPosts.add((Post) postHashOps.get(Constants.POST_KEY, postId));
		}
		return userPosts;
	}
	
	private Long createNewPostId() {
		return idOps.increment(Constants.POST_ID_KEY, 1);
	}

	public LinkedHashSet<Post> getAllPosts() {
		List<Object> objects = postHashOps.values(Constants.POST_KEY);
		LinkedHashSet<Post> posts = new LinkedHashSet<Post>();
		objects.forEach(o -> posts.add((Post)o));
		return posts;
	}

}
