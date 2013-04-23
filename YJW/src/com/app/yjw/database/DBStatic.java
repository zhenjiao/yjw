/*
 * 
 *     Created by 亦恒 陶 
 *  Copyright (c) 2012年 . All rights reserved.
 *  
 */
package com.app.yjw.database;

public class DBStatic {

	// 数据库表
	public static String AccountTableName = "account";
	public static String MessageTableName = "message";
	public static String DealTableName = "deal";

	// 数据库表的列名
	public static String[] AccountTableColumns = new String[] { "id", "phone",
			"sid" };
	public static String[] MessageTableColumns = new String[] { "id", "dealid",
			"chat_user", "msg", "type" };
	public static String[] DealTableColumns = new String[] {
			"id",
			"deal_id",
			"type"/* Received (Direct Forward / Forward ) || Create || Forward */,
			"title", "content", "expeir_date","creator","referfee","commissionfee","creatorphone","creatorname","dealstate" };

	public static String CreateAccountTable = "CREATE TABLE "
			+ AccountTableName
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, phone TEXT, sid TEXT)";

	public static String CreateMessageTable = "CREATE TABLE "
			+ MessageTableName
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, dealid TEXT, chat_user TEXT, msg TEXT, type TEXT)";

	public static String CreateDealTable = "CREATE TABLE "
			+ DealTableName
			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, deal_id TEXT, type TEXT ,"
			+ " title TEXT, content TEXT, expeir_date TEXT , "
			+ " creator TEXT, referfee TEXT, commissionfee TEXT, creatorphone TEXT, creatorname TEXT, dealstate TEXT)";
	
	
	public static String generateSQLForDeleteAccount() {
		return "delete from " + AccountTableName + " where id <> -1 ";
	}

	public static String generateSQLForDeleteMessage() {
		return "delete from " + MessageTableName + " where id <> -1 ";
	}

	public static String generateSQLForDeleteMessage(int id) {
		return "delete from" + MessageTableName + "where id = " + id;
	}

}
