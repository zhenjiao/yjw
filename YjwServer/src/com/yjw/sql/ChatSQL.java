package com.yjw.sql;

import com.yjw.bean.ChatBean;
import com.yjw.tool.BeanPacker;

public class ChatSQL {
	/* 加入一个chat */
	public String setChat(ChatBean chatBean) {
		/*return "Insert into yjw_chat(from_phone,to_user,content,deal_id) values('"
				+ chatBean.getFrom_phone()
				+ "','"
				+ chatBean.getTo_phone()
				+ "','"
				+ chatBean.getContent()
				+ "','"
				+ chatBean.getDeal_id()
				+ "')";*/
		return new BeanPacker(chatBean).insert("yjw_chat");
	}

	/**
	 * 获取未读的消息
	 */
	public String getUnreadMsg(String id) {
		return "Select * from yjw_chat where is_read='0' and to_id=" + id + " order by deal,sub_time";
	}

	/**
	 * 获取未读的消息的size
	 */
	public String getUnreadMsgCount(String id) {
		return "select count(deal_id) from yjw_chat where is_read='0' and to_id=" + id;
	}
	
	public String getPhoneNumber(String id){
		return "select cellphone from yjw_user where id=" + id; 
	}
}
