package com.yjw.bean;

public class MessageBean {
	public enum MsgType {from, to}
	
	private MsgType type;
	private Integer deal;
	private int from_id;
	private int to_id;
	private String content;
	
	public MsgType getType() {
		return type;
	}
	public void setType(MsgType type) {
		this.type = type;
	}
	public Integer getDeal() {
		return deal;
	}
	public void setDeal(Integer deal) {
		this.deal = deal;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
