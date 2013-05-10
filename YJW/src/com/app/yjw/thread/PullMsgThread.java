/*
 * Created By Yiheng Tao
 * PullMsgThread.java
 * 
 * TODO Need reconstruct!!
 * 同学。。这叫 refactor。。。。
 */

package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.app.yjw.YJWActivity;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.G;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.ChatBean;

public class PullMsgThread extends YJWBaseThread {

	private final String TAG = "PullMsgThread";

	public enum Mode {
		Background/* 后台运行 */, Running/* 程序启动但为打开聊天 */, Displaying/* 聊天 */
	}

	private Mode mode;
	private String displayDealId;
	private String currentChatUser;

	public void setMode(Mode m) {
			this.mode = m;
	}

	public Mode getMode() {
		return mode;
	}

	public void setDisplayDealId(String id) {
		this.displayDealId = id;
	}

	public void setChatUser(String user) {
		this.currentChatUser = user;
	}

	@Override
	public void run() {
		while (G.getIsRun()) {
			try {
				Log.d("bkdthread", mode.name());
				super.run();
				if (mode == Mode.Background)Thread.sleep(60000);
				else if (mode == Mode.Running)Thread.sleep(20000);
				else Thread.sleep(10000);
			} catch (InterruptedException e) {
				Log.d("PullMsgThread","Interrupted");
			}
		}
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("id", YJWActivity.user.getId().toString()));
		parameters.add(new BasicNameValuePair("flag", "getMsg"));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CHAT;
	}
	
	@Override
	protected void init() {
		RegisterError(ErrorCode.E_NULL_MSG,YJWMessage.YJWMESSAGE_NULL,null);		
	}
	
	@Override
	protected void OnSuccess() {
		Log.d(TAG, currentChatUser + " ---  " + displayDealId);
		List<ChatBean> currentChatList = new ArrayList<ChatBean>();
		List<ChatBean> notCurrentChatList = new ArrayList<ChatBean>();
		for (int i = 1;i<back.length; ++i) {
			ChatBean chat =(ChatBean)new BeanPacker(back[i]).getBean();
			if (mode == Mode.Displaying	&& chat.getDeal().equals(displayDealId)
					&& (currentChatUser != null && chat.getFrom_id().equals(currentChatUser)))
				currentChatList.add(chat);
			else
				notCurrentChatList.add(chat);
		}
		msg.what = YJWMessage.GET_MESSAGE_SUCCESS.ordinal();		
		if (mode == Mode.Displaying && handler != null) {
			List<ChatBean> list = new ArrayList<ChatBean>();
			if (currentChatList.size() != 0) {
				list.addAll(currentChatList);
			}
			if (notCurrentChatList.size() != 0) {
				list.addAll(notCurrentChatList);
			}
			msg.obj=list;
			if (list.size()==0) msg.what = YJWMessage.YJWMESSAGE_NULL.ordinal();				
		} else if (mode == Mode.Running) {
			msg.obj = notCurrentChatList;
		}
		super.OnSuccess();
	}
}
