package com.yjw.impl;

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
import com.yjw.dao.EntityDAO;
import com.yjw.sql.EntitySQL;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.TemplateGetter;

public abstract class EntityImpl implements EntityDAO {

	protected JdbcTemplate jdbcTemplate;
	protected EntitySQL sql;

	public EntityImpl(EntitySQL sql) {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = sql;
	}

	/* Add Deal */
	public int add(BeanPacker packer) {
		try{
			final BeanPacker PACKER=packer;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			//sql.add(packer)
			if (jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
					 return connection.prepareStatement(sql.add(PACKER),Statement.RETURN_GENERATED_KEYS);
				}
			},keyHolder)!=-1) return keyHolder.getKey().intValue();
			return -1;
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

		// 获取分页信息
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
