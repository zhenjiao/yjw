package com.yjw.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.yjw.bean.ChatBean;

public interface ChatDAO {

	/* ����һ��chat */
	public boolean setChat(ChatBean chatBean);

	/**
	 * ��ȡδ������Ϣ
	 */
	public HashMap<String, Object> getUnreadMsg(String id);

	/**
	 * �鿴�Ƿ�������Ϣ
	 */
	public int getUnreadMsgCount(String id);

	/**
	 * ����Ϣ����Ϊ�Ѷ�
	 */
	public boolean setIsRead(ArrayList<Integer> list);
	
	public String getCellphoneById(String id);

}