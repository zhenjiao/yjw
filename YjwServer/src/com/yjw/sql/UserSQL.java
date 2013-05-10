package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.tool.BeanPacker;

public class UserSQL implements EntitySQL{
	
	/*
	public String getReceivedDeal(int user_id,int startCount,int endCount){
		return "select * from (select * from yjw_deal where id in (select deal_id from yjw_deal_intr where" +
				"  phone_number = (select cellphone from yjw_user where id='"+user_id+"'))) as deal" +
						" LEFT JOIN (select a.name,a.cellphone,b.type,b.deal_id,b.user_id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
						"as b where a.id = b.user_id and b.phone_number=(select cellphone from yjw_user where id='"
						+user_id+"')) as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
	}
	
	public String getRecDealCount(int user_id,int startCount,int endCount){
		return "select count(id) from (select id  from yjw_deal where id in (select deal_id from yjw_deal_intr where" +
				"  phone_number = (select cellphone from yjw_user where id='"+user_id+"')) LIMIT "+startCount+","+endCount+
				") as a";
	}
	
	
	public String getPublishedDeal(int user_id,int startCount,int endCount){
		return "select * from (select * from yjw_deal where user_id='" + user_id +"') as deal" +
						" LEFT JOIN (select a.name,b.phone_number as cellphone,b.type,b.deal_id,a.id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
												"as b where a.cellphone = b.phone_number ) as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
	}
	
	
	public String getPublishedDealCount(int user_id,int startCount,int endCount){
		return"select count(id) from (select id from yjw_deal where user_id='"
				+ user_id + "' LIMIT " + startCount + "," + endCount + ") as a";
	}
	
	
	public String getForwardDeal(int user_id,int startCount,int endCount){
		return  "select * from (select * from yjw_deal where id in (select deal_id from yjw_deal_intr " +
				"where user_id = '"+user_id+"' and type = 'FWD')) as deal" +
				" LEFT JOIN (select a.name,b.phone_number as cellphone,b.type,b.deal_id,a.id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
				"as b where a.cellphone = b.phone_number and b.user_id='"
				+user_id+"') as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
				//"select * from yjw_deal where id in (select deal_id from yjw_deal_intr " +
				//"where user_id = '"+user_id+"' and type = 'FWD') LIMIT "+startCount+","+endCount+"";
	}
	
	public String getForwardDealCount(int user_id,int startCount,int endCount){
		return "select count(id) from (select id from yjw_deal where id in (select deal_id from yjw_deal_intr " +
		"where user_id = '"+user_id+"' and type = 'FWD') LIMIT "+startCount+","+endCount+") as a";
	}*/
	
	public String get(int id){
		return "SELECT id,name,cellphone,email,balance,timestamps FROM yjw_user where id="+id;
	}
	
	public String getByCellphone(String cellphone) {
		return "SELECT id,name,cellphone,email,balance,timestamps FROM yjw_user where cellphone="+cellphone;
	}

	public String add(BeanPacker packer) {
		// TODO Auto-generated method stub
		return null;
	}

	public String del(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String sync(GetInfoBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
