package com.yjw.sql;

import com.yjw.bean.UserBean;

public class prepareSQL {
	
	public String register(UserBean userBean) {
		return "insert into yjw_user(cellphone,sid) values('"
				+ userBean.getCellphone() +
				 "','" + userBean.getSid() + "')";
	}
	/*�ж��ֻ������Ƿ��ظ�*/
	public String cellPhoneDup(String cellphone) {
		return "select count(id) from yjw_user where cellphone='" + cellphone
				+ "'";
	}
}
