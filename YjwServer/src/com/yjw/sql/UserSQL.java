package com.yjw.sql;

public class UserSQL {
	
	/*获取别人推荐的生意*/
	public String getReceivedDeal(int user_id,int startCount,int endCount){
		return "select * from (select * from yjw_deal where id in (select deal_id from yjw_deal_intr where" +
				"  phone_number = (select cellphone from yjw_user where id='"+user_id+"'))) as deal" +
						" LEFT JOIN (select a.name,a.cellphone,b.type,b.deal_id,b.user_id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
						"as b where a.id = b.user_id and b.phone_number=(select cellphone from yjw_user where id='"
						+user_id+"')) as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
	}
	/*计算此页还剩余的生意*/
	public String getRecDealCount(int user_id,int startCount,int endCount){
		return "select count(id) from (select id  from yjw_deal where id in (select deal_id from yjw_deal_intr where" +
				"  phone_number = (select cellphone from yjw_user where id='"+user_id+"')) LIMIT "+startCount+","+endCount+
				") as a";
	}
	
	/*获取自己发布的生意*/
	public String getPublishedDeal(int user_id,int startCount,int endCount){
		return "select * from (select * from yjw_deal where user_id='" + user_id +"') as deal" +
						" LEFT JOIN (select a.name,b.phone_number as cellphone,b.type,b.deal_id,a.id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
												"as b where a.cellphone = b.phone_number ) as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
	}
	
	/*获取生意发布消息的数量*/
	public String getPublishedDealCount(int user_id,int startCount,int endCount){
		return"select count(id) from (select id from yjw_deal where user_id='"
				+ user_id + "' LIMIT " + startCount + "," + endCount + ") as a";
	}
	
	/*获取自己转发的生意*/
	public String getForwardDeal(int user_id,int startCount,int endCount){
		return  "select * from (select * from yjw_deal where id in (select deal_id from yjw_deal_intr " +
				"where user_id = '"+user_id+"' and type = 'FWD')) as deal" +
				" LEFT JOIN (select a.name,b.phone_number as cellphone,b.type,b.deal_id,a.id as fwd_user_id,b.rec_status as rec_status from yjw_user as a,yjw_deal_intr " +
				"as b where a.cellphone = b.phone_number and b.user_id='"
				+user_id+"') as number ON deal.id = number.deal_id LIMIT "+startCount+","+endCount+"";
				//"select * from yjw_deal where id in (select deal_id from yjw_deal_intr " +
				//"where user_id = '"+user_id+"' and type = 'FWD') LIMIT "+startCount+","+endCount+"";
	}
	/*获取剩余的转发生意的数目*/
	public String getForwardDealCount(int user_id,int startCount,int endCount){
		return "select count(id) from (select id from yjw_deal where id in (select deal_id from yjw_deal_intr " +
		"where user_id = '"+user_id+"' and type = 'FWD') LIMIT "+startCount+","+endCount+") as a";
	}
	
	
}
