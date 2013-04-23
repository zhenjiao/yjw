package com.yjw.impl;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.UserBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.sql.prepareSQL;

/**
 * ע��ķ�װ����
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

	// ע����Ϣ�������ݿ�
	public boolean register(UserBean userBean) {
		boolean flag = false;
		// �������ݿ�
		int i = jdbcTemplate.update(this.prepareSQL.register(userBean));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// �жϸ��ֻ����Ƿ��Ѿ�����
	public boolean isDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(this.prepareSQL
				.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}

	// ��¼
	public String logon(UserBean userBean) {
		String msg = "";
		if (isDuplicate(userBean.getCellphone())) {
			Map<String, Object> map = this.jdbcTemplate
					.queryForMap("select password,sid,isActive from yjw_user where cellphone='"
							+ userBean.getCellphone() + "'");

			if (map.get("password").toString().equals(userBean.getPassword())) {
				if (map.get("isActive").toString().equals("0")) {
					msg = "���ʺ�δ����";
				} else {
					msg = map.get("sid").toString();
				}

			} else {
				msg = "�ʺŻ������벻��ȷ";
			}
		} else {
			msg = "���˻�������";
		}
		return msg;
	}

	// ������֤��Ϣ
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
