package com.yjw.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.dao.BalanceDAO;
import com.yjw.sql.DealSQL;
import com.yjw.tool.TemplateGetter;

public class BalanceImpl implements BalanceDAO{
	
	private JdbcTemplate jdbcTemplate;
	private DealSQL sql;
	
	public BalanceImpl() {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = new DealSQL();
	}

	public float getBalance(String sid) {
		/*// TODO Auto-generated method stub
		List commission=this.jdbcTemplate.queryForList(sql.getCommission(sid));
		//List fee=this.jdbcTemplate.queryForList(sql.getfee(sid));
		float sumcom=0,sumfee=0;
		Iterator itc = commission.iterator();
		//Iterator itf = fee.iterator();
		while(itc.hasNext()){
			Map map = (Map) itc.next();
			sumcom=sumcom+Float.parseFloat(map.get("commission").toString());
		}
		
		/*while(itf.hasNext()){
			Map map = (Map) itf.next();
			sumfee=sumfee+Float.parseFloat(map.get("fee").toString());
		}*/
			
		//return sumfee;//sumcom-sumfee;
		return 0;
		
	}

}
