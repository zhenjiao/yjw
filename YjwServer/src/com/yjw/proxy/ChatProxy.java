package com.yjw.proxy;

import java.util.ArrayList;
import java.util.HashMap;

import com.yjw.bean.ChatBean;
import com.yjw.dao.ChatDAO;
import com.yjw.impl.ChatImpl;

public class ChatProxy implements ChatDAO {

	private ChatDAO chatDAO;

	public ChatProxy() {
		this.chatDAO = new ChatImpl();
	}

	public boolean setChat(ChatBean chatBean) {
		return this.chatDAO.setChat(chatBean);
	}
	/**
	 * 获取未读的消息
	 */
	public  HashMap<String, Object> getUnderReadMsg(String phoneNumber){
		return this.chatDAO.getUnderReadMsg(phoneNumber);
	}

	/**
	 * 查看是否有新信息
	 */
	public  int getUnderReadMsgSize(String phoneNumber){
		return this.chatDAO.getUnderReadMsgSize(phoneNumber);
	}
	
	/**
	 * 把信息设置为已读
	 */
	public boolean setIsRead(ArrayList<Integer> list){
		return this.chatDAO.setIsRead(list);
	} 
}
