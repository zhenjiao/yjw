package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfTransFrom;
import com.yjw.sql.adapter.IfTransOwner;
import com.yjw.sql.adapter.IfTransTo;
import com.yjw.sql.adapter.OrAdapter;
import com.yjw.sql.adapter.WhereAdapter;
import com.yjw.tool.BeanPacker;

public class TransSQL implements EntitySQL{
	
	public String sync(GetInfoBean bean){
		return sync(bean,
				new IfTransFrom(bean.getId()),
				new IfTransTo(bean.getId(), true),
				new IfTransOwner(bean.getId(),false)
		);
	}
	
	public String sync(GetInfoBean bean,WhereAdapter... conditions) {
		return "select id from yjw_trans where "+
				new OrAdapter(conditions)+
				" ORDER BY timestamps DESC "+"LIMIT "+ bean.getPage()*PAGE_SIZE + "," + PAGE_SIZE;
	}
	
	public String get(int id){
		return "select * from yjw_trans where id='"+id+"'";
	}
	
	public String del(int id){
		return "delete from yjw_trans where id='"+id+"'";
	}
	
	public String add(BeanPacker packer){
		return packer.insert("yjw_trans");
	}
	
	public String confirm(int id){
		return "update yjw_trans set confirmed='1' where id='"+id+"'";
	}

}
