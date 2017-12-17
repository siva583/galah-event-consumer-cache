package com.intuit.galah.model;

public class Fault {
	
	Integer errorCode;
	String errorMessage;
	
	public Fault() {
		super();
	}

	public Fault(Integer errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "Fault [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	};
	
	
	
	

}
