package com.yjw.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.yjw.bean.ChatBean;

public interface ChatDAO {

	/* ����һ��chat */
	public abstract boolean setChat(ChatBean chatBean);

	/**
	 * ��ȡδ������Ϣ
	 */
	public abstract HashMap<String, Object> getUnderReadMsg(String phoneNumber);

	/**
	 * �鿴�Ƿ�������Ϣ
	 */
	public abstract int getUnderReadMsgSize(String phoneNumber);

	/**
	 * ����Ϣ����Ϊ�Ѷ�
	 */
	public abstract boolean setIsRead(ArrayList<Integer> list);

}