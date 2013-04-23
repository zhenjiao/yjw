package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Looper;
import android.os.Message;

import com.app.yjw.YJWActivity;
import com.app.yjw.json.JSONParser;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.ExpandDealInfo;

public class SyncDealThread extends YJWBaseThread {

	private SyncType type;
	private int pageIndex;

	public enum SyncType {
		Received, Recommended, Referred
	}

	public SyncDealThread(int _pageIndex, SyncType _type) {
		pageIndex = _pageIndex;
		type = _type;
	}

	@Override
	public void run() {
		List<BasicNameValuePair> parameters = this.generateParameters();
		String back_str = NetworkFactory.getInstance().doPost(
				generateURL(), parameters,true);
		Message msg = generateMsg(back_str);
		this.sendMessage(msg);
	}

	private Message generateMsg(String back_str) {
		Message msg = Message.obtain();
		if (type == SyncType.Recommended) {
			List<ExpandDealInfo> list = new ArrayList<ExpandDealInfo>();
			try {
				JSONObject json = new JSONObject(back_str);
				for (int i = 1;; i++) {
					if (json.has(new Integer(i).toString())) {
						JSONObject obj = json.getJSONObject(new Integer(i)
								.toString());
						list.add(JSONParser.parseJsonToExpandDealInfo(obj));
					} else
						break;
				}
				msg.what = 1;
				msg.obj = list;
			} catch (JSONException e) {
				e.printStackTrace();
				msg.what = -1;
			}
		} else {
			List<DealInfo> list = new ArrayList<DealInfo>();
			try {
				JSONObject json = new JSONObject(back_str);
				for (int i = 1;; i++) {
					if (json.has(new Integer(i).toString())) {
						JSONObject obj = json.getJSONObject(new Integer(i)
								.toString());
						list.add(JSONParser.parseJsonToDeal(obj));
					} else
						break;
				}
				msg.what = 1;
				msg.obj = list;
			} catch (JSONException e) {
				e.printStackTrace();
				msg.what = -1;
			}
		}
		return msg;
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters
				.add(new BasicNameValuePair("sid", YJWActivity.user.getSid()));
		parameters.add(new BasicNameValuePair("pageIndex", new Integer(
				pageIndex).toString()));
		switch (type) {
		case Received:
			parameters.add(new BasicNameValuePair("dealType", "REC"));
			break;
		case Recommended:
			parameters.add(new BasicNameValuePair("dealType", "PUB"));
			break;
		case Referred:
			parameters.add(new BasicNameValuePair("dealType", "FRW"));
			break;
		}
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SYNCDEAL;
	}
}
