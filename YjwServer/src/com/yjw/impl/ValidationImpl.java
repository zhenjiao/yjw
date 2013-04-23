package com.yjw.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.tool.GetJdbcTemplate;

public class ValidationImpl {
	private JdbcTemplate jdbcTemplate;

	public ValidationImpl() {
		this.jdbcTemplate = new GetJdbcTemplate().getJtl();
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

	// ��֤�������֤���Ƿ���ȷ,�����ȷ�ͼ����û�
	public boolean validate(String sid, String code) {
		boolean flag = false;
		String getValidateRecordSQL = "select * from yjw_validation where sid='"
				+ sid + "'";
		List<?> list = this.jdbcTemplate.queryForList(getValidateRecordSQL);
		if (list.size() == 1) {
			Map<?, ?> map = (Map<?, ?>) list.get(0);
			if (code.equals(map.get("validation_code"))) {
				if (activeUser(sid)) {
					deleteValidationRecord(sid);
					flag = true;
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	// ���û�����Ϊ����״̬
	public boolean activeUser(String sid) {
		String sql = "update yjw_user set isActive='1' where sid='" + sid + "'";
		int returnCode = this.jdbcTemplate.update(sql);
		if (returnCode > 0) {
			return true;
		} else {
			return false;
		}
	}

	// ɾ��һ���û�����֤����Ŀ
	public boolean deleteValidationRecord(String sid) {
		String deleteSQL = "delete from yjw_validation where sid='" + sid + "'";
		int i = this.jdbcTemplate.update(deleteSQL);
		if (i > -1) {
			return true;
		} else {
			return false;
		}
	}

	// �����û�������������
	public boolean setPasswordAndName(String sid, String password, String name) {
		String sql = "update yjw_user set name='" + name + "',password='"
				+ password + "' where sid='" + sid + "'";
		int i = this.jdbcTemplate.update(sql);
		if(i>-1){
			return true;
		}else{
			return false;
			
		}
	}

}
