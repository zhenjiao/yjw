package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfAdapter;
import com.yjw.sql.adapter.Order;
import com.yjw.tool.BeanPacker;

public abstract class BaseSQL {
	public final int PAGE_SIZE = 20;
	public abstract String DBName();
	public abstract String sync(GetInfoBean bean);
	public String sync(GetInfoBean bean,String fields){return null;}
	public String fields(){return "*";}
	
	public String log(String s){
		System.out.println(s);
		return s;
	}
	
	public String add(BeanPacker packer){
		return log(packer.insert(DBName()));
	}
	public String get(int id){
		return log("SELECT "+fields()+" FROM "+DBName()+" WHERE id='"+id+"'");		
	}
	public String del(int id){
		return log("DELETE FROM "+DBName()+" WHERE id='"+id+"'"); 
	}
	public String sync(GetInfoBean bean,IfAdapter condition){
		return log("SELECT id FROM "+DBName()+" WHERE "+condition+
				new Order(bean.getPage(),PAGE_SIZE,bean.getEsc()));
	}	
}
