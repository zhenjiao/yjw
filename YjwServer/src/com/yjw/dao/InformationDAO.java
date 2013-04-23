package com.yjw.dao;

import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.sql.UserSQL;
import com.yjw.tool.GetJdbcTemplate;

/*作为所有获取关于一个用户的信息的接口*/
public interface InformationDAO {
	public JdbcTemplate jdbcTemplate = new GetJdbcTemplate().getJtl();
	public UserSQL userSQL =  new UserSQL();
	public JSONObject getInformation(int user_id, int pageIndex);
	
}
