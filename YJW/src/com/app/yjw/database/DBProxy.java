/*
 *     Created by “‡∫„ Ã’ 
 *  Copyright (c) 2012ƒÍ . All rights reserved.
 *  
 *  This class is used to combine all the database manipulations.
 */

package com.app.yjw.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.app.yjw.YJWActivity;
import com.app.yjw.pojo.BaseDealInfo.DealState;
import com.app.yjw.pojo.BaseUserInfo;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.ExpandDealInfo;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.pojo.UserInfo;
import com.app.yjw.pojo.MsgInfo.MsgType;

public class DBProxy {

	/**
	 * @description given the dealId and chatWith(in phone number format),
	 *              return the list of the messages in the database
	 * @name getHistoryMessage
	 * @param
	 * @category database select
	 * @author Yiheng Tao
	 */
	public static List<MsgInfo> getHistoryMessage(String dealId, String chatWith) {
		List<MsgInfo> msgList = new ArrayList<MsgInfo>();
		Cursor cur = YJWActivity.database.query(DBStatic.MessageTableName,
				DBStatic.MessageTableColumns, "dealid = " + dealId
						+ " and chat_user = " + chatWith, null, null, null,
				null);
		while (cur.moveToNext()) {
			MsgInfo chat = new MsgInfo(cur.getString(3), cur.getString(4)
					.equals("from") ? MsgType.FROM : MsgType.TO);
			Log.d("char", chat.getMsg());
			msgList.add(chat);
		}
		return msgList;
	}

	/**
	 * @description insert into database with the given dealid and phone number
	 *              to chat and the message
	 * @name insertChatMessage
	 * @param
	 * @category database insertion
	 * @author Yiheng Tao
	 */
	public static void insertChatMessage(String dealId, String chatWith,
			MsgInfo msg) {
		ContentValues cv = new ContentValues();
		cv.put("dealid", dealId);
		cv.put("chat_user", chatWith);
		cv.put("type", msg.getType().equals(MsgType.FROM) ? "from" : "to");
		cv.put("msg", msg.getMsg());
		YJWActivity.database.insert(DBStatic.MessageTableName, null, cv);
	}
	
	/**
	 * insert the account into the database
	 * @param id
	 * @param phone
	 * @param sid
	 */
	public static void insertNewAccount(String phone, String sid)
	{
		ContentValues cv = new ContentValues();
		cv.put("phone", phone);
		cv.put("sid", sid);
		YJWActivity.database.insert(DBStatic.AccountTableName, null, cv);
	}
	
	public static void insertDeals(List<DealInfo> list, String type){
		for(DealInfo deal:list){
			Cursor cur = YJWActivity.database.query(DBStatic.DealTableName,
					DBStatic.DealTableColumns, "deal_id = "+deal.getId()+" and type = '"+type+"'",null,null,null,null);
			if (cur != null && 0 != cur.getCount()) {
				//Log.d("dealInfo","already exists");
			} else {
				ContentValues cv = new ContentValues();
				cv.put("type", type);
				cv.put("deal_id", deal.getId());
				cv.put("title", deal.getTitle());
				cv.put("content", deal.getDetails());
				cv.put("expeir_date", deal.getDate().toString());
				cv.put("creator", deal.getCreator());
				cv.put("referfee", deal.getReferFee());
				cv.put("commissionfee", deal.getCommissionFee());
				cv.put("creatorphone", deal.getCreatorPhone());
				cv.put("creatorname", deal.getCreatorName());
				switch(deal.getDealState()){
				case Confirmed:
					cv.put("dealstate", "Confirmed");
					break;
				case NotConfirmed:
					cv.put("dealstate", "NotConfirmed");
					break;
				case NeedNoConfirm:
					cv.put("dealstate", "NeedNoConfirm");
					break;
				}

				YJWActivity.database.insert(DBStatic.DealTableName, null, cv);
			}		
		}
	}
	
	public static void insertDeals(List<ExpandDealInfo>list){
		for(ExpandDealInfo expdeal:list){
			DealInfo deal = expdeal.getDeal();
			List<BaseUserInfo> user_list= expdeal.getReplyers();
			Cursor cur = YJWActivity.database.query(DBStatic.DealTableName,
					DBStatic.DealTableColumns, "deal_id = "+deal.getId()+" and type = 'PUB'",null,null,null,null);
			if (cur != null && 0 != cur.getCount()) {
				//Log.d("dealInfo","already exists");
			} else {
				ContentValues cv = new ContentValues();
				cv.put("type", "PUB");
				cv.put("deal_id", deal.getId());
				cv.put("title", deal.getTitle());
				cv.put("content", deal.getDetails());
				cv.put("expeir_date", deal.getDate().toString());
				cv.put("creator", deal.getCreator());
				cv.put("referfee", deal.getReferFee());
				cv.put("commissionfee", deal.getCommissionFee());
				switch(deal.getDealState()){
				case Confirmed:
					cv.put("dealstate", "Confirmed");
					break;
				case NotConfirmed:
					cv.put("dealstate", "NotConfirmed");
					break;
				case NeedNoConfirm:
					cv.put("dealstate", "NeedNoConfirm");
					break;
				}
				for(BaseUserInfo user: user_list){
					cv.put("creatorphone", deal.getCreatorPhone());
					cv.put("creatorname", deal.getCreatorName());
					YJWActivity.database.insert(DBStatic.DealTableName, null, cv);
				}
			}		
		}
	}
}
