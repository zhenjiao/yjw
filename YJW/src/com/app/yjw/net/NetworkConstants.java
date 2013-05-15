package com.app.yjw.net;

public class NetworkConstants {
	//public static String SERVER_URL = "http://sselab.tongji.edu.cn/";//"http://192.168.1.110:8080/";//"http://sselab.tongji.edu.cn/";//"http://10.0.2.2:8080/";//"http://sselab.tongji.edu.cn/";
	//public static String SERVER_URL = "http://192.168.71.1:8080/";
	public static String SERVER_URL = "http://www.1step2leads.com:8080/";
	//public static String SERVER_URL = "http://apache:secret@ec2-54-251-222-148.ap-southeast-1.compute.amazonaws.com/git/yjw/";
	public static String SERVER_IP = "http://54.251.240.3:8080";
	public static String SERVER_APPNAME = "yjwServer";

	
	public static final String URL_REGISTER = SERVER_URL + SERVER_APPNAME
			+ "/RegisterAction";
	public static final String URL_VALIDATECODE = SERVER_URL + SERVER_APPNAME
			+ "/GetValidateCodeAction";
	public static final String URL_LOGIN = SERVER_URL + SERVER_APPNAME
			+ "/LoginAction";
	public static final String URL_CHAT = SERVER_URL + SERVER_APPNAME 
			+ "/ChatAction";
	
	public static final String URL_SYNCUSER = SERVER_URL + SERVER_APPNAME
			+ "/SyncUserAction";
	public static final String URL_GETUSER = SERVER_URL + SERVER_APPNAME
			+ "/GetUserAction";
	public static final String URL_GETUSERBYPHONE = SERVER_URL + SERVER_APPNAME
			+ "/GetUserByPhoneAction";
	
	public static final String URL_SYNCTRANS = SERVER_URL + SERVER_APPNAME
			+ "/SyncTransAction";
	public static final String URL_GETTRANS = SERVER_URL + SERVER_APPNAME
			+ "/GetTransAction";
	public static final String URL_ADDTRANS = SERVER_URL + SERVER_APPNAME
			+ "/AddTransAction";
	public static final String URL_DELTRANS = SERVER_URL + SERVER_APPNAME
			+ "/DelTransAction";
	public static final String URL_CONFTRANS = SERVER_URL + SERVER_APPNAME
			+ "/ConfTransAction";
	
	public static final String URL_SYNCDEAL = SERVER_URL + SERVER_APPNAME
			+ "/SyncDealAction";	
	public static final String URL_ADDDEAL = SERVER_URL + SERVER_APPNAME
			+ "/AddDealAction";
	public static final String URL_DELDEAL = SERVER_URL + SERVER_APPNAME
			+ "/DelDealAction";
	public static final String URL_GETDEAL = SERVER_URL + SERVER_APPNAME
			+ "/GetDealAction";
	
	public static final String URL_FORWARD = SERVER_URL + SERVER_APPNAME
			+ "/ForwardDealAction";	
	public static final String URL_SHAREDUSER = SERVER_URL + SERVER_APPNAME
			+ "/GetDealSharedUsersAction";
	public static final String URL_ACCEPT = SERVER_URL + SERVER_APPNAME
			+ "/AcceptDealAction";
	public static final String URL_DECLINE = SERVER_URL + SERVER_APPNAME
			+ "/DeclineDealAction";
//	public static String URL_REGISTERVALIDATE = SERVER_URL + SERVER_APPNAME
//			+ "/RegisterValidateAction";
//	public static String URL_RESENDVALIDATE = SERVER_URL + SERVER_APPNAME
//			+ "/ResendValidateMsgAction";
//	public static String URL_FINISHREGISTER = SERVER_URL + SERVER_APPNAME
//			+ "/FinishRegisterAction";
	public static final String URL_CHECKVERSION = SERVER_URL + SERVER_APPNAME
			+ "/OutputVersionAction";
	public static final String URL_FETCHBALANCE = SERVER_URL + SERVER_APPNAME
			+ "/OutputBalanceAction";
	
}
