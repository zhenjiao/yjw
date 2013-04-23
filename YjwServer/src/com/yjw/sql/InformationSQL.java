package com.yjw.sql;

import com.yjw.bean.DealBean;


public class InformationSQL {
	/* Add Deal */
	public String addDeal(DealBean dealBean) {
		return "insert into yjw_deal(user_id,title,content,fee,commission,req_confirm) "
				+ "values('" + dealBean.getUser_id() + "','"
				+ dealBean.getTitle() + "','" + dealBean.getContent() + "','"
				+ dealBean.getFee() + "','" + dealBean.getCommission() + "','" + dealBean.getReq_confirm() +"')";
	}

	/* Delete a deal */
	public String delDeal(int id) {
		return "delete from yjw_deal where id='" + id + "'";
	}

	/* Sync Deal */
	public String syncDeal(int user_id, int startCount, int endCount) {
		return "select * from yjw_deal where user_id='" + user_id + "' LIMIT "
				+ startCount + "," + endCount + "";
	}

	public String getSyncCount(int user_id, int startCount, int endCount) {
		return "select count(id) from (select id from yjw_deal where user_id='"
				+ user_id + "' LIMIT " + startCount + "," + endCount + ") as a";
	}

	/* Update Deal */
	public String updateDeal(DealBean dealBean) {
		return "update ";
	}

	/* Forward Deal */
/*	public String forwardDeal(int deal_id,int for_user_id, String phoneNumber) {
		return "insert into yjw_deal_frw(deal_id,for_user_id,phone_number) values('"
				+ deal_id + "','"+ for_user_id + "','" + phoneNumber + "')";
	}*/
	public String forwardDeal(int deal_id,int for_user_id, String phoneNumber) {
		return "insert into yjw_deal_intr(deal_id,user_id,phone_number) values('"
				+ deal_id + "','"+ for_user_id + "','" + phoneNumber + "')";
	}
	
	public String getSharedUser(String dealId){
		return "select name,cellphone from yjw_user where cellphone in(select from_phone from yjw_chat" +
				" where deal_id = '"+dealId+"' group by from_phone)";
	}
	
	/*
	 * get balance
	 */
	
	public String getCommission(String sid){
		
		return "select commission from yjw_deal where user_id=(select user_id from yjw_user where sid ='"+sid+"')" ;	
	}
	
	public String getfee( String sid){
	
		return "select fee from yjw_deal where user_id=(select user_id from yjw_user where sid ='"+sid+"')" ;	
	}
	
	//get all users
	
	public String getUserphone( ){
		
		return "select cellphone from yjw_user" ;	
	}
}
