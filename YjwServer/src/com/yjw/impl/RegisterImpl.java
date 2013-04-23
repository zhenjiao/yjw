package com.yjw.impl;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.UserBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.sql.prepareSQL;

/**
 * 注册的封装代码
 * 
 * @author Glorabit
 * 
 */
public class RegisterImpl implements RegisterDAO {

	private JdbcTemplate jdbcTemplate;
	private prepareSQL prepareSQL;

	public RegisterImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.prepareSQL = new prepareSQL();
	}

	// 注册信息插入数据库
	public boolean register(UserBean userBean) {
		boolean flag = false;
		// 插入数据库
		int i = jdbcTemplate.update(this.prepareSQL.register(userBean));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// 判断该手机号是否已经存在
	public boolean isDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(this.prepareSQL
				.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// 登录
	public String logon(UserBean userBean) {
		String msg = "";
		if (isDuplicate(userBean.getCellphone())) {
			Map<String, Object> map = this.jdbcTemplate
					.queryForMap("select password,sid,isActive from yjw_user where cellphone='"
							+ userBean.getCellphone() + "'");

			if (map.get("password").toString().equals(userBean.getPassword())) {
				if (map.get("isActive").toString().equals("0")) {
					msg = "该帐号未激活";
				} else {
					msg = map.get("sid").toString();
				}

			} else {
				msg = "帐号或者密码不正确";
			}
		} else {
			msg = "该账户不存在";
		}
		return msg;
	}

	// 插入验证信息
	public boolean insertValidateCode(String sid, String code) {
		boolean flag = false;
		String insertSQL = "insert into yjw_validation(sid,validation_code) values('"
				+ sid + "','" + code + "')";
		String deleteSQL = "delete from yjw_validation where sid='" + sid + "'";
		int deleteReturn = this.jdbcTemplate.update(deleteSQL);
		if (deleteReturn > -1) {
			int insertReturn = this.jdbcTemplate.update(insertSQL);
			if (insertReturn > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
