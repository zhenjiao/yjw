package com.yjw.bean;

public class ChatBean {
	private int chat_id;
	private String from_phone;
	private String to_phone;
	private String content;
	private String is_read;
	private String sub_time;
	private int deal_id;
	
	public int getChat_id() {
		return chat_id;
	}
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	public int getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(int deal_id) {
		this.deal_id = deal_id;
	}
	public String getFrom_phone() {
		return from_phone;
	}
	public void setFrom_phone(String from_phone) {
		this.from_phone = from_phone;
	}
	public String getTo_phone() {
		return to_phone;
	}
	public void setTo_phone(String to_phone) {
		this.to_phone = to_phone;
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
	
	
}
