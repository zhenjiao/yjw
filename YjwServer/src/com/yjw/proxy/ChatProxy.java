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
	 * ��ȡδ������Ϣ
	 */
	public  HashMap<String, Object> getUnderReadMsg(String phoneNumber){
		return this.chatDAO.getUnderReadMsg(phoneNumber);
	}

	/**
	 * �鿴�Ƿ�������Ϣ
	 */
	public  int getUnderReadMsgSize(String phoneNumber){
		return this.chatDAO.getUnderReadMsgSize(phoneNumber);
	}
	
	/**
	 * ����Ϣ����Ϊ�Ѷ�
	 */
	public boolean setIsRead(ArrayList<Integer> list){
		return this.chatDAO.setIsRead(list);
	} 
}
