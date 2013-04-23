package com.yjw.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.dao.BalanceDAO;
import com.yjw.sql.InformationSQL;
import com.yjw.tool.GetJdbcTemplate;

public class BalanceImpl implements BalanceDAO{
	
	private JdbcTemplate jdbcTemplate;
	private InformationSQL sql;
	
	public BalanceImpl() {
		this.jdbcTemplate = new GetJdbcTemplate().getJtl();
		this.sql = new InformationSQL();
	}

	public float getBalance(String sid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> commission=this.jdbcTemplate.queryForList(sql.getCommission(sid));
		//List fee=this.jdbcTemplate.queryForList(sql.getfee(sid));
		float sumcom=0,sumfee=0;
		Iterator<?> itc = commission.iterator();
		//Iterator itf = fee.iterator();
		while(itc.hasNext()){
			Map<String, Object> map = (Map<String, Object>) itc.next();
			sumcom=sumcom+Float.parseFloat(map.get("commission").toString());
		}
		
		/*while(itf.hasNext()){
			Map map = (Map) itf.next();
			sumfee=sumfee+Float.parseFloat(map.get("fee").toString());
		}*/
			
		return sumfee;//sumcom-sumfee;
	}

}
