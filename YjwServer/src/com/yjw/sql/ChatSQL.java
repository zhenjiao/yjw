package com.yjw.sql;

import com.yjw.bean.ChatBean;

public class ChatSQL {
	/* ����һ��chat */
	public String setChat(ChatBean chatBean) {
		return "Insert into yjw_chat(from_phone,to_user,content,deal_id) values('"
				+ chatBean.getFrom_phone()
				+ "','"
				+ chatBean.getTo_phone()
				+ "','"
				+ chatBean.getContent()
				+ "','"
				+ chatBean.getDeal_id()
				+ "')";
	}

	/**
	 * ��ȡδ������Ϣ
	 */
	public String getUnderReadMsh(String phoneNumber) {
		return "Select * from yjw_chat where is_read='0' and to_user='"
				+ phoneNumber + "' order by deal_id,sub_time";
	}

	/**
	 * ��ȡδ������Ϣ��size
	 */
	public String getUnderReadMsgSize(String phoneNumber) {
		return "select count(deal_id) from yjw_chat where is_read='0' and to_user='"
				+ phoneNumber + "'";
	}
}
