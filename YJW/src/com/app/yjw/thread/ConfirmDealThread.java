package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.util.YJWMessage;

public class ConfirmDealThread extends YJWBaseThread {

	private String dealId;
	private boolean accept;

	public ConfirmDealThread(String dealId, Handler handler, boolean accept) {
		this.dealId = dealId;
		this.handler = handler;
		this.accept = accept;
	}

	@Override
	public void run() {
		List<BasicNameValuePair> parameters = this.generateParameters();
		String back_str = NetworkFactory.getInstance()
				.doPost(generateURL(), parameters,true);
		msg = Message.obtain();
		if (back_str.equalsIgnoreCase("success")) {
			msg.what = accept?YJWMessage.CONFIRM_ACCEPT_SUCCESS.ordinal():YJWMessage.CONFIRM_DECLINE_SUCCESS.ordinal();
		} else {
			msg.what = accept?YJWMessage.CONFIRM_ACCEPT_FAILURE.ordinal():YJWMessage.CONFIRM_DECLINE_FAILURE.ordinal();
		}
		this.sendMessage();
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getId().toString()));
		parameters.add(new BasicNameValuePair("dealid", dealId));
		return parameters;
	}

	@Override
	protected String generateURL() {
		if(accept)
		{
			return NetworkConstants.URL_ACCEPT;
		}
		else
		{
			return NetworkConstants.URL_DECLINE;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
