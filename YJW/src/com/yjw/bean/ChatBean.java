package com.yjw.bean;

public class ChatBean {
	
	private int chat_id;
	private int deal;
	private int from_id;
	private int to_id;
	private String content;
	private String is_read;
	private String sub_time;
	
	
	public Integer getChat_id() {
		return chat_id;
	}
	public void setChat_id(Integer chat_id) {
		this.chat_id = chat_id;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	public String getSub_time() {
		return sub_time;
	}
	public void setSub_time(String sub_time) {
		this.sub_time = sub_time;
	}
	
	public Integer getDeal() {
		return deal;
	}
	public void setDeal(Integer deal) {
		this.deal = deal;
	}
	public Integer getFrom_id() {
		return from_id;
	}
	public void setFrom_id(Integer from_id) {
		this.from_id = from_id;
	}
	public Integer getTo_id() {
		return to_id;
	}
	public void setTo_id(Integer to_id) {
		this.to_id = to_id;
	}
}
