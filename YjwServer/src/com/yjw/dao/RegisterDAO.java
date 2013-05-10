package com.yjw.dao;

import com.yjw.bean.AccountBean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;

public interface RegisterDAO {
	public boolean register(UserBean userBean);
	public boolean isDuplicate(String cellphone);
	public String logon(AccountBean bean);
	public boolean insertValidateCode(ValidationBean bean);
	public boolean validate(String sid,String code);
}
