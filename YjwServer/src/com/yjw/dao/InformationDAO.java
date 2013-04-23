package com.yjw.dao;

import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.sql.UserSQL;
import com.yjw.tool.GetJdbcTemplate;

/*��Ϊ���л�ȡ����һ���û�����Ϣ�Ľӿ�*/
public interface InformationDAO {
	public JdbcTemplate jdbcTemplate = new GetJdbcTemplate().getJtl();
	public UserSQL userSQL =  new UserSQL();
	public JSONObject getInformation(int user_id, int pageIndex);
	
}
