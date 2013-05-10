/*
 * Created By Yiheng Tao
 * DealDetailPageActivity.java
 */
package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.json.JSONParser;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.BaseUserInfo;
import com.app.yjw.util.YJWMessage;

public class GetReplyUserThread extends YJWBaseThread {

	private String deal_id;

	public GetReplyUserThread(String did) {
		this.deal_id = did;
	}

	@Override
	public void run() {
		String back_str = NetworkFactory.getInstance().doPost(
				generateURL(), generateParameters(),true);

		msg = Message.obtain();
		// fucking design at server!
		if (back_str.equals("No deal from this user.")) {
			msg.what = YJWMessage.GET_SHARED_USER_NONE.ordinal();
		} else {
			try {
				List<BaseUserInfo> userList = JSONParser
						.parseJsonToBaseUserInfoList(back_str);
				msg.what = YJWMessage.GET_MESSAGE_SUCCESS.ordinal();
				msg.obj = userList;
			} catch (JSONException e) {
				msg.what = YJWMessage.GET_MESSAGE_FAILURE.ordinal();
			}
		}
		sendMessage();

	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("dealId", deal_id));
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getId().toString()));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SHAREDUSER;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
