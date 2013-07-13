package com.yjw.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.AccountBean;
import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;
import com.yjw.sql.RegisterSQL;
import com.yjw.util.ErrorCode;
import com.yjw.util.TemplateGetter;

/**
 * ע��ķ�װ����
 * 
 * @author Glorabit
 * 
 */
public class RegisterDAO {

	private JdbcTemplate jdbcTemplate;
	private RegisterSQL sql;

	public RegisterDAO() {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = new RegisterSQL();
	}

	// ע����Ϣ�������ݿ�
	public boolean register(UserBean userBean) {
		boolean flag = false;
		// �������ݿ�
		try{
			int i = jdbcTemplate.update(sql.register(userBean));
			if (i != 0) {
				flag = true;
			}
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	// �жϸ��ֻ����Ƿ��Ѿ�����
	public boolean isCellphoneDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(sql.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}
	
	public boolean isNameDuplicate(String name) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(sql.cellNameDup(name));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}	

	// ��¼
	@SuppressWarnings("unchecked")
	public String login(AccountBean bean) {
		String msg = "";
		if (isCellphoneDuplicate(bean.getCellphone())) {
			String sqlstr=sql.Login(bean);
			try{
				Map<String,?> map= this.jdbcTemplate.queryForMap(sqlstr);
				msg = ErrorCode.E_SUCCESS+"&"+Bean.Pack(map,UserBean.class);
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

	// ������֤��Ϣ
	public boolean insertValidateCode(ValidationBean bean) {
		String insertSQL = sql.insertValidateCode(bean);
		int insertReturn = this.jdbcTemplate.update(insertSQL);
		return insertReturn > 0;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validate(String sid, String code) {
		boolean flag = false;
		String getValidateRecordSQL = sql.validate(sid);
		List<Map<String,Object>> list = this.jdbcTemplate.queryForList(getValidateRecordSQL);
		if (list.size() == 1) {
			Map<String,?> map = list.get(0);
			if (code.equals(map.get("validation_code"))) {
					flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
