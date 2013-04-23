package com.yjw.bean;

public class ValidationBean {
	private int user_id;
	private String validation_code;
	private String timestamp;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getValidation_code() {
		return validation_code;
	}

	public void setValidation_code(String validation_code) {
		this.validation_code = validation_code;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
