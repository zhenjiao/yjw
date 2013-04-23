package com.yjw.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.yjw.bean.ChatBean;

public interface ChatDAO {

	/* 加入一条chat */
	public abstract boolean setChat(ChatBean chatBean);

	/**
	 * 获取未读的消息
	 */
	public abstract HashMap<String, Object> getUnderReadMsg(String phoneNumber);

	/**
	 * 查看是否有新信息
	 */
	public abstract int getUnderReadMsgSize(String phoneNumber);

	/**
	 * 把信息设置为已读
	 */
	public abstract boolean setIsRead(ArrayList<Integer> list);

}