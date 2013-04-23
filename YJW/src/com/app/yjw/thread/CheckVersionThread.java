package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Looper;
import android.os.Message;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.util.Utility;
import com.yjw.bean.Version;

public class CheckVersionThread extends YJWBaseThread {

	private Context context;

	public void setContext(Context _context) {
		this.context = _context;
	}

	@Override
	public void run() {
		Version version = (Version) NetworkFactory.getInstance().doPostObject(
				generateURL(), generateParameters(),true);
		Message msg = Message.obtain();
		msg.what = Utility.CompareVersion(context, version);
		msg.obj = version;
		sendMessage(msg);
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		return new ArrayList<BasicNameValuePair>();
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CHECKVERSION;
	}

}