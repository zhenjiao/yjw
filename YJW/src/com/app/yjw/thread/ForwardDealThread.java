package com.app.yjw.thread;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;

public class ForwardDealThread extends YJWBaseThread {

	private String[] phones;
	private String deal_id;

	public ForwardDealThread(String id, String[] ph, Handler h) {
		this.phones = ph;
		this.deal_id = id;
		this.handler = h;
	}

	@Override
	public void run() {
		String str = NetworkFactory.getInstance().doPost(
				generateURL(), generateParameters(),false);
		if(str!=null){
			msg = Message.obtain();
			if (str.equals("success"))
				msg.what = 1;
			else
				msg.what = 0;
			this.sendMessage();
		}
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new LinkedList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getId().toString()));
		parameters.add(new BasicNameValuePair("dealid", deal_id));
		String phone_str = "";
		for (int i = 0; i < phones.length; ++i)
		{
			phone_str = phone_str + phones[i] + ",";
		}
		phone_str.replace(" ", "");
		phone_str.replace("-", "");
		phone_str = phone_str.substring(0, phone_str.length() - 1);
		parameters.add(new BasicNameValuePair("phoneToForward", phone_str));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_FORWARD;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
