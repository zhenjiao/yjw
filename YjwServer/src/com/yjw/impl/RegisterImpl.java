package com.yjw.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.AccountBean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.sql.RegisterSQL;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.ErrorCode;
import com.yjw.tool.TemplateGetter;

/**
 * 注册的封装代码
 * 
 * @author Glorabit
 * 
 */
public class RegisterImpl implements RegisterDAO {

	private JdbcTemplate jdbcTemplate;
	private RegisterSQL sql;

	public RegisterImpl() {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = new RegisterSQL();
	}

	// 注册信息插入数据库
	public boolean register(UserBean userBean) {
		boolean flag = false;
		// 插入数据库
		int i = jdbcTemplate.update(sql.register(userBean));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// 判断该手机号是否已经存在
	public boolean isDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(sql.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// 登录
	public String logon(AccountBean bean) {
		String msg = "";
		if (isDuplicate(bean.getCellphone())) {
			String sqlstr=sql.Logon(bean);
			try{
				Map map= this.jdbcTemplate.queryForMap(sqlstr);
				msg = ErrorCode.E_SUCCESS+"&"+new BeanPacker(map,UserBean.class);
			}catch(IncorrectResultSizeDataAccessException e){		
				msg = ErrorCode.E_LOGIN_FAILED.toString();
			}catch(DataAccessException e){
				msg = ErrorCode.E_LOGIN_FAILED.toString();
				System.out.println("please check table: yjw_user");
				e.printStackTrace();
			}
		}else{
			msg = ErrorCode.E_LOGIN_FAILED.toString();
		}
		return msg;
	}

	// 插入验证信息
	public boolean insertValidateCode(ValidationBean bean) {
		boolean flag = false;
		String insertSQL = sql.insertValidateCode(bean);
		int insertReturn = this.jdbcTemplate.update(insertSQL);
		return insertReturn > 0;
	}
	
	public boolean validate(String sid, String code) {
		boolean flag = false;
		String getValidateRecordSQL = sql.validate(sid);
		List list = this.jdbcTemplate.queryForList(getValidateRecordSQL);
		if (list.size() == 1) {
			Map map = (Map) list.get(0);
			if (code.equals(map.get("validation_code"))) {
					flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
