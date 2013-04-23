/*
 * Created By Yiheng Tao
 * PullMsgThread.java
 * 
 * TODO Need reconstruct!!
 */

package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.app.yjw.YJWActivity;
import com.app.yjw.json.JSONParser;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.util.YJWMessage;

public class PullMsgThread extends YJWBaseThread {

	private final String TAG = "PullMsgThread";
	private boolean isRun = true;

	public enum Mode {
		Background/* 后台运行 */, Running/* 程序启动但为打开聊天 */, Displaying/* 聊天 */
	}

	private Handler displayHandler;
	private Mode mode;
	private String displayDealId;
	private String currentChatUser;

	public void setMode(Mode m) {
		this.mode = m;
	}

	public Mode getMode() {
		return mode;
	}

	public void setDisplayHandler(Handler handler) {
		this.displayHandler = handler;
	}

	public void setDisplayDealId(String id) {
		this.displayDealId = id;
	}

	public void setChatUser(String user) {
		this.currentChatUser = user;
	}

	private void dispatchMsg(String backStr) {
		if (backStr.equals("Null"))
			return;
		Log.d(TAG, currentChatUser + " ---  " + displayDealId);
		List<MsgInfo> currentChatList = new ArrayList<MsgInfo>();
		List<MsgInfo> notCurrentChatList = new ArrayList<MsgInfo>();
		Message msg = Message.obtain();
		try {
			JSONObject json = new JSONObject(backStr);
			for (int i = 1;; ++i) {
				Integer iObj = new Integer(i);
				if (json.has(iObj.toString())) {
					MsgInfo chat = JSONParser.parseJsonToReceivedChat((json
							.getJSONObject(iObj.toString())));
					if (mode == Mode.Displaying
							&& chat.getDealId().equals(displayDealId)
							&& (currentChatUser != null && chat.getFromPhone()
									.equals(currentChatUser)))
						currentChatList.add(chat);
					else
						notCurrentChatList.add(chat);
				} else
					break;
			}
			msg.what = YJWMessage.GET_MESSAGE_SUCCESS;
		} catch (Exception e) {
			msg.what = YJWMessage.GET_MESSAGE_FAILURE;
		}
		if (mode == Mode.Displaying && displayHandler != null) {
			if (currentChatList.size() != 0) {
				msg.obj = currentChatList;
				displayHandler.sendMessage(msg);
			}
			if (notCurrentChatList.size() != 0) {
				Message _msg = Message.obtain();
				_msg.what = YJWMessage.GET_MESSAGE_SUCCESS;
				_msg.obj = notCurrentChatList;
				this.sendMessage(_msg);
			}
		} else if (mode == Mode.Running) {
			msg.obj = notCurrentChatList;
			this.sendMessage(msg);
		}
	}

	@Override
	public void run() {
		while (isRun) {
			try {
				Log.d("bkdthread", "running");
				synchronized (this) {
					String backStr = NetworkFactory.getInstance().doPost(
							generateURL(), generateParameters(),true);
					// receive message
					dispatchMsg(backStr);
					if (mode == Mode.Background)
						Thread.sleep(60000);
					else if (mode == Mode.Running)
						Thread.sleep(20000);
					else
						Thread.sleep(10000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getSid()));
		parameters.add(new BasicNameValuePair("flag", "getMsg"));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CHAT;
	}
	
	public void stopThread(){
		this.isRun = false;
	}
}
