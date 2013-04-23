package com.app.yjw.net;

public class NetworkConstants {
	//public static String SERVER_URL = "http://sselab.tongji.edu.cn/";//"http://192.168.1.110:8080/";//"http://sselab.tongji.edu.cn/";//"http://10.0.2.2:8080/";//"http://sselab.tongji.edu.cn/";
	public static String SERVER_URL = "http://zhen-laptop.fareast.corp.microsoft.com:8080/";
	public static String SERVER_IP = "http://";
	public static String SERVER_APPNAME = "YjwServer";

	public static String URL_REGISTER = SERVER_URL + SERVER_APPNAME
			+ "/RegisterAction";
	public static String URL_ADDDEAL = SERVER_URL + SERVER_APPNAME
			+ "/AddDealAction";
	public static String URL_LOGIN = SERVER_URL + SERVER_APPNAME
			+ "/LoginAction";
	public static String URL_DELETEDEAL = SERVER_URL + SERVER_APPNAME
			+ "/DelDealAction";
	public static String URL_SYNCDEAL = SERVER_URL + SERVER_APPNAME
			+ "/GetDealAction";
	public static String URL_FORWARD = SERVER_URL + SERVER_APPNAME
			+ "/ForwardDealAction";
	public static String URL_CHAT = SERVER_URL + SERVER_APPNAME + "/ChatAction";
	public static String URL_SHAREDUSER = SERVER_URL + SERVER_APPNAME
			+ "/GetDealSharedUsersAction";
	public static String URL_ACCEPT = SERVER_URL + SERVER_APPNAME
			+ "/AcceptDealAction";
	public static String URL_DECLINE = SERVER_URL + SERVER_APPNAME
			+ "/DeclineDealAction";
	public static String URL_REGISTERVALIDATE = SERVER_URL + SERVER_APPNAME
			+ "/RegisterValidateAction";
	public static String URL_RESENDVALIDATE = SERVER_URL + SERVER_APPNAME
			+ "/ResendValidateMsgAction";
	public static String URL_FINISHREGISTER = SERVER_URL + SERVER_APPNAME
			+ "/FinishRegisterAction";
	public static String URL_CHECKVERSION = SERVER_URL + SERVER_APPNAME
			+ "/OutputVersionAction";
	public static String URL_FETCHBALANCE = SERVER_URL + SERVER_APPNAME
			+ "/OutputBalanceAction";
}
