package com.intuit.galah.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	
	private String userId;
	private String email;
	private String userName;
	private String firstName;
	private String lastName;
	private String middleName;
	private Date createdAt;
	private Date updatedAt;
	
		
	public User() {
		super();
	}

	public User(String userId, String email, String userName, String firstName, String lastName, String middleName, Date createdAt,
			Date updatedAt) {
		super();
		this.userId = userId;
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", userName=" + userName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleName=" + middleName + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

}
