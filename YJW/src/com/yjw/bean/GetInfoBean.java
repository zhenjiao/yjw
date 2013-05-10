package com.yjw.bean;

import java.sql.Timestamp;

public class GetInfoBean {
	private int id;
	private Integer page;
	private Timestamp timestamps;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Timestamp getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}
}
