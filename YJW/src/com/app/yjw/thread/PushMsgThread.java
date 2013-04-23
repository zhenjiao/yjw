package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.util.YJWMessage;

public class PushMsgThread extends YJWBaseThread {

	MsgInfo chat;
	DealInfo deal;
	String to_user;

	public PushMsgThread(Handler handler) {
		this.handler = handler;
	}

	public void setChat(MsgInfo chat) {
		this.chat = chat;
	}

	public void setDeal(DealInfo deal) {
		this.deal = deal;
	}

	public void setToUser(String to) {
		this.to_user = to;
	}

	public void run() {
		String backStr = NetworkFactory.getInstance().doPost(
				generateURL(), generateParameters(),false);
		
		if(backStr!=null){
			Message msg = Message.obtain();
			// send message
			if (!backStr.equals("success"))
				msg.what = YJWMessage.SEND_MESSAGE_FAILURE;
			else
				msg.what = YJWMessage.SEND_MESSAGE_SUCCESS;
			this.sendMessage(msg);
		}
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getSid()));
		parameters.add(new BasicNameValuePair("to_user", to_user));
		parameters.add(new BasicNameValuePair("content", chat.getMsg()));
		parameters.add(new BasicNameValuePair("deal_id", deal.getId()));
		parameters.add(new BasicNameValuePair("flag", "setMsg"));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CHAT;
	}
}
