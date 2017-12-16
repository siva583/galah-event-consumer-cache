package com.intuit.galah.model;

import java.util.Date;

public class Follow {
	
	private String userId;
	private String fId;
	private Date date;
	
	
	
	public Follow() {
		super();
	}

	public Follow(String userId, String fId, Date date) {
		super();
		this.userId = userId;
		this.fId = fId;
		this.date = date;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Follow [userId=" + userId + ", fId=" + fId + ", date=" + date + "]";
	}
	
	

}
