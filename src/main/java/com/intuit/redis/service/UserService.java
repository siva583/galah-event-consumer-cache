package com.intuit.redis.service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.intuit.galah.constants.Constants;
import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;

@Service
public class UserService {
	
	 @Autowired
	 private RedisTemplate<String, Object> template;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> userHashOps;
	
	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> userFollowOps;
	
	@Resource(name = "redisTemplate")
	private ListOperations<String, String> userListOps;

	@Resource(name="redisTemplate")
	private ValueOperations<String, Long> idOps;
	
	

	public User createUser(User user) {
		user.setUserId(String.valueOf(createNewUserId()));
		System.out.println("id::"+user.getUserId());
		userHashOps.put(Constants.USER_KEY, user.getUserId(), user);
		return user;
	}
	
	public User updateUser(User user) {
		userHashOps.put(Constants.USER_KEY, user.getUserId(), user);
		return user;
	}

	public User getUser(String id) {
		return (User) userHashOps.get(Constants.USER_KEY, id);
	}

	public boolean deleteUser(String userId) {
		//clean up following, follower, posts in redis
		userFollowOps.remove(Constants.FOLLOWER+userId);
		userFollowOps.remove(Constants.FOLLOWING + userId);
		userFollowOps.remove(Constants.FOLLOW_REQ+userId);
		userHashOps.delete(Constants.USER_KEY, userId);
		return true;
	}

	public void addFollower(String userId, Date date, String wantToFolllow) {
		userFollowOps.add(Constants.FOLLOWER+ wantToFolllow, userId,date.getTime());
		userFollowOps.add(Constants.FOLLOWING + userId, wantToFolllow,date.getTime());
		userFollowOps.remove(Constants.FOLLOW_REQ+wantToFolllow,userId);
		
	}

	public void addRequestToFollow(String userId, Date date, String wantToFolllow) {
		userFollowOps.add(Constants.FOLLOW_REQ+wantToFolllow, userId,date.getTime());
	}

	public void deleteFollower(String userId, String wantToUnFolllow) {
		userFollowOps.remove(Constants.FOLLOWER+ wantToUnFolllow, userId);
		userFollowOps.remove(Constants.FOLLOWING+ userId, wantToUnFolllow);
	}
	
	public LinkedHashSet<User> getFollowersForUser(String userId) {
		Set<String> followerIds = userFollowOps.range(Constants.FOLLOWER+userId, 0, -1);
		LinkedHashSet<User> followers = new LinkedHashSet<User>();
		for(String followerId:followerIds) {
			followers.add((User) userHashOps.get(Constants.USER_KEY, followerId));
		}
		
		return followers;
	}
	
	public LinkedHashSet<User> getFollowingForUser(String userId){
		Set<String> followingIds = userFollowOps.range(Constants.FOLLOWING+userId, 0, -1);
		LinkedHashSet<User> following = new LinkedHashSet<User>();
		for(String followingId:followingIds) {
			following.add((User) userHashOps.get(Constants.USER_KEY, followingId));
		}
		
		return following;
	}
	
	public LinkedHashSet<User> getFollowingReqsForUser(String userId){
		Set<String> requestorIds = userFollowOps.range(Constants.FOLLOW_REQ+userId, 0, -1);
		LinkedHashSet<User> requestors = new LinkedHashSet<User>();
		for(String reqId:requestorIds) {
			requestors.add((User) userHashOps.get(Constants.USER_KEY, reqId));
		}
		
		return requestors;
	}

	public Set<String> getFollwerIdsForUser(String userId) {
		return userFollowOps.range(Constants.FOLLOWER+userId, 0, -1);
	}

	public LinkedHashSet<Post> getUserTimeline(String userId) {
		List<String> postIds = userListOps.range(Constants.TIMELINE_KEY+userId, 0, -1);
		LinkedHashSet<Post> timelinePosts = new LinkedHashSet<Post>();
		for(String postId : postIds) {
			timelinePosts.add((Post) userHashOps.get(Constants.POST_KEY, postId));
		}
		return timelinePosts;
	}
	
	private Long createNewUserId() {
		return idOps.increment(Constants.USER_ID_KEY, 1);
	}

	public LinkedHashSet<User> getAllUsers() {
			List<Object> objects = userHashOps.values(Constants.USER_KEY);
			LinkedHashSet<User> users = new LinkedHashSet<User>();
			objects.forEach(o -> users.add((User)o));
			return users;
	}

}
