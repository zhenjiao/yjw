package com.yjw.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.yjw.bean.ChatBean;

public interface ChatDAO {

	/* 加入一条chat */
	public boolean setChat(ChatBean chatBean);

	/**
	 * 获取未读的消息
	 */
	public HashMap<String, Object> getUnreadMsg(String id);

	/**
	 * 查看是否有新信息
	 */
	public int getUnreadMsgCount(String id);

	/**
	 * 把信息设置为已读
	 */
	public boolean setIsRead(ArrayList<Integer> list);
	
	public String getCellphoneById(String id);

}