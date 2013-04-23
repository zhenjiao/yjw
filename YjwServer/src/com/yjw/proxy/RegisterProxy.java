package com.yjw.proxy;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.UserBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.impl.RegisterImpl;

public class RegisterProxy implements RegisterDAO {
	private RegisterDAO registerDAO;

	public RegisterProxy(JdbcTemplate jdbcTemplate) {
		this.registerDAO = new RegisterImpl(jdbcTemplate);
	}

	public boolean register(UserBean userBean) {
		return this.registerDAO.register(userBean);
	}

	public boolean isDuplicate(String cellphone) {
		return this.registerDAO.isDuplicate(cellphone);
	}

	public String logon(UserBean userBean) {
		return this.registerDAO.logon(userBean);
	}
	
	public boolean insertValidateCode(String sid, String code){
		return this.registerDAO.insertValidateCode(sid, code);
	}
}
