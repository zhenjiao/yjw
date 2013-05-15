package com.yjw.dao;

import java.util.Map;

import com.yjw.bean.UserBean;
import com.yjw.sql.UserSQL;
import com.yjw.tool.BeanPacker;

public class UserDAO extends BaseDAO {
	public UserDAO() {
		super(new UserSQL());
	}
	@Override
	public Class<?> getBeanClass() {
		return UserBean.class;
	}
	public BeanPacker getByCellphone(String cellphone) {
		try{
			Map<String,?> map = jdbcTemplate.queryForMap(((UserSQL)sql).getByCellphone(cellphone));
			return new BeanPacker(map,UserBean.class);
		}catch(Exception e){
				return null;
		}
	}

	

/*	public BeanPacker getUser(int id) {
		try{
			Map<String,?> map=jdbcTemplate.queryForMap(sql.getUser(id));
			return new BeanPacker(map,UserBean.class);
		}catch(Exception e){
			return null;
		}
	}*/

}
