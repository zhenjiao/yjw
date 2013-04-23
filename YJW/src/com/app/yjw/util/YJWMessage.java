/*
 * Created By Yiheng Tao
 * YJWMessage.java
 * 
 * This class defined some messages will be used in sending messages to handler
 * each of will has a number which should be equal in both sending side and receiving side
 * please add more messages if needed
 * 
 */

package com.app.yjw.util;

public class YJWMessage {

	/* messages used in PushMsgThread.java and DealReplyPageActivity.java */
	public static final int SEND_MESSAGE_SUCCESS = 1;
	public static final int SEND_MESSAGE_FAILURE = 2;

	/* messages used in PullMsgThread.java and YJWActivity.java */
	public static final int GET_MESSAGE_SUCCESS = 3;
	public static final int GET_MESSAGE_NULL = 4;
	public static final int GET_MESSAGE_FAILURE = 5;

	/* messages used in GetSharedUserThread.java and DealDetailPageActivity.java */
	public static final int GET_SHARED_USER_SUCCESS = 6;
	public static final int GET_SHARED_USER_NONE = 7;
	public static final int GET_SHARED_USER_FAILURE = 8;

	public static final int CONFIRM_ACCEPT_SUCCESS = 9;
	public static final int CONFIRM_ACCEPT_FAILURE = 10;
	public static final int CONFIRM_DECLINE_SUCCESS = 11;
	public static final int CONFIRM_DECLINE_FAILURE = 12;
	
	/* messages used in Register */
	public static final int REGISTER_SUCCESS = 13;
	public static final int REGISTER_FAILURE = 14;

	/* fetch balance */
	public static final int FETCH_BALANCE_SUCCESS = 15;
	public static final int FETCH_BALANCE_FAILURE = 16;
	
	/* check update */
	public static final int NEW_UPDATE = 17;
	
	/* network messages*/
	public static final int NET_FAIL_RECONNECT = 18;
	public static final int NET_FAIL_NORECONNECT = 19;
}
