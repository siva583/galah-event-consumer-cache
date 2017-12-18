package com.intuit.galah.iface;

import java.util.LinkedHashSet;

import com.intuit.galah.model.Post;

public interface IPostService {
	
	public Post createPost(Post post);
	public void deletePost(String postId);
	public LinkedHashSet<Post> getAllPosts();
	public Post getPost(String postId);
	public LinkedHashSet<Post> getUserPosts(String userId);
	public Post updatePost(Post post);
	
}
