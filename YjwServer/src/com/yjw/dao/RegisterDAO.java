package com.yjw.dao;

import com.yjw.bean.UserBean;

public interface RegisterDAO {
	public boolean register(UserBean userBean);
	public boolean isDuplicate(String cellphone);
	public String logon(UserBean userBean);
	public boolean insertValidateCode(String sid, String code);
}
