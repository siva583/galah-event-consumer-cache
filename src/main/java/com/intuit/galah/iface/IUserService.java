package com.intuit.galah.iface;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.intuit.galah.model.Post;
import com.intuit.galah.model.User;

public interface IUserService {
	
	public User createUser(User user);	
	public User updateUser(User user);	
	public User getUser(String id);	
	public boolean deleteUser(String userId);
	public void addFollower(String userId, Date date, String wantToFolllow);
	public void addRequestToFollow(String userId, Date date, String wantToFolllow);
	public void deleteFollower(String userId, String wantToUnFolllow);
	public LinkedHashSet<User> getFollowersForUser(String userId);
	public LinkedHashSet<User> getFollowingForUser(String userId);
	public LinkedHashSet<User> getFollowingReqsForUser(String userId);
	public Set<String> getFollwerIdsForUser(String userId);
	public LinkedHashSet<Post> getUserTimeline(String userId, Integer pageSize, Integer pageNumber);
	public LinkedHashSet<User> getAllUsers();

}
