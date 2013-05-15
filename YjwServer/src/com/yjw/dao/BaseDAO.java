package com.yjw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.BaseSQL;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.TemplateGetter;

public abstract class BaseDAO {

	protected JdbcTemplate jdbcTemplate;
	protected BaseSQL sql;

	public BaseDAO(BaseSQL sql) {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = sql;
	}

	/* Add Deal */
	public int add(BeanPacker packer) {
		try{
			final BeanPacker PACKER=packer;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			//sql.add(packer)
			int ret=jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
					 return connection.prepareStatement(sql.add(PACKER),Statement.RETURN_GENERATED_KEYS);
				}
			},keyHolder);
			if (ret==-1)return -1; else
			if (ret==0) return 0; else
			return keyHolder.getKey().intValue();
		}catch(Exception e){
			return -1;
		}
		
	}
	/* Delete Deal */
	public boolean del(int id) {
		boolean flag = false;
		if (this.jdbcTemplate.update(this.sql.del(id)) != 0) {
			flag = true;
		}
		return flag;
	}

	/* Get User's Deal */
	public List<Integer> sync(GetInfoBean bean) {
		String sqlstr=sql.sync(bean);
		System.out.println(sqlstr);
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sqlstr);
		List<Integer> ret=new ArrayList<Integer>();
		for (Map<String,?> map:list){
			ret.add((Integer)map.get("id"));
		}
		return ret;
	}
	
	public BeanPacker get(int id) {
		try{
		Map<String,?> map = jdbcTemplate.queryForMap(sql.get(id));
		return new BeanPacker(map,getBeanClass());
		}catch(Exception e){
			return null;
		}
	}
	
	public abstract Class<?> getBeanClass();

}
