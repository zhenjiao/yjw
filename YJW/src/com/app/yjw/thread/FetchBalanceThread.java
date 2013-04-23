package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Looper;
import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.Balance;

public class FetchBalanceThread extends YJWBaseThread {

	@Override
	public void run() {
		Message msg = Message.obtain();

		try {
			Balance balance = (Balance) NetworkFactory.getInstance()
					.doPostObject(generateURL(), generateParameters(),true);

			msg.what = YJWMessage.FETCH_BALANCE_SUCCESS;
			msg.obj = balance;
		} catch (Exception e) {
			msg.what = YJWMessage.FETCH_BALANCE_FAILURE;
		}
		this.sendMessage(msg);
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getSid()));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_FETCHBALANCE;
	}

}
