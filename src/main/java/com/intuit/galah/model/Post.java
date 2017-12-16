package com.intuit.galah.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1993537531377336608L;
	private String postId;
	private String userId;
	private String content;
	private Date createdAt;
	
	
	
	public Post() {
		super();
	}

	public Post(String postId, String userId, String content, Date createdAt) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", userId=" + userId + ", content=" + content + ", createdAt=" + createdAt
				+ "]";
	}
	
	
	
}
