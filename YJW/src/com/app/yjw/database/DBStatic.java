/*
 * 
 *     Created by ��� �� 
 *  Copyright (c) 2012�� . All rights reserved.
 *  
 */
package com.app.yjw.database;

import java.lang.reflect.Method;

import android.util.Log;

public class DBStatic {

	// ���ݿ��
	public static String AccountTableName = "account";
	public static String MessageTableName = "message";
	public static String DealTableName = "deal";
	public static String UserTableName = "user";
	public static String TransTableName = "trans";

	// ���ݿ�������
	//public static String[] AccountTableColumns = new String[] { "cellphone", "password" };
	public static String[] MessageTableColumns = new String[] { "id", "deal", "from_id", "to_id", "content", "type" };
	//public static String[] DealTableColumns = new String[] {
		//	"id", "deal_id", "type"/* Received (Direct Forward / Forward ) || Create || Forward */,
			//"title", "content", "expeir_date","creator","referfee","commissionfee",
			//"creatorphone","creatorname","dealstate" };

	public static String CreateAccountTable = "CREATE TABLE IF NOT EXISTS "+ AccountTableName	+ 
			" (" +
			" cellphone TEXT," +
			" password TEXT" +
			" )";

	public static String CreateMessageTable = "CREATE TABLE IF NOT EXISTS "
			+ MessageTableName
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, deal INTEGER, from_id INTEGER, to_id INTEGER," 
			+ " content TEXT, type TEXT)";

	public static String CreateDealTable = "CREATE TABLE "
			+ DealTableName
			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, deal_id TEXT, type TEXT ,"
			+ " title TEXT, content TEXT, expeir_date TEXT , "
			+ " creator TEXT, referfee TEXT, commissionfee TEXT, creatorphone TEXT, creatorname TEXT, dealstate TEXT)";
	
	
	public static String clearAccountTable = "delete from " + AccountTableName;

	public static String generateSQLForDeleteMessage() {
		return "delete from " + MessageTableName + " where id <> -1 ";
	}

	public static String generateSQLForDeleteMessage(int id) {
		return "delete from" + MessageTableName + "where id = " + id;
	}

	public static String CreateTableByBean(Class<?> beanclass,String tableName){
		String s="CREATE TABLE IF NOT EXISTS "+tableName+" ";
		Method[] methods=beanclass.getMethods();
		boolean first=true;
		for (Method method:methods){
			if (method.getName().substring(0,3).equals("get")&&!method.getName().equals("getClass")){
				if (first) {
					s+="(";
					first=false;
				}else
					s+=",";
				s+=method.getName().substring(3).toLowerCase()+" TEXT";
			}
		}
		s+=")";
		Log.i("Database",s);
		return s;
	}
}
