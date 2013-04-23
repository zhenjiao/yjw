package com.yjw.bean;

import java.sql.Date;

public class DealBean {
	private int id;
	private int user_id;
	private String title;
	private String content;
	private float fee;
	private float commission;
	private String valid_period;
	private String timestamp;
	private String req_confirm;
	private Date expire_date;

	public String getReq_confirm() {
		return req_confirm;
	}

	public void setReq_confirm(String req_confirm) {
		this.req_confirm = req_confirm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}

	public String getValid_period() {
		return valid_period;
	}

	public void setValid_period(String valid_period) {
		this.valid_period = valid_period;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Date getexpire_date() {
		return expire_date;
	}

	public void setexpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

}
