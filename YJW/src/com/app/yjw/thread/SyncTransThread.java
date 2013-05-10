package com.app.yjw.thread;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.G;
import com.app.yjw.util.YJWMessage;

public class SyncTransThread extends YJWBaseThread {

	public enum SyncType {
		Referred, Recommended, Received
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SYNCTRANS;
	}
	
	@Override
	protected void OnSuccess() {
		synchronized (G.transes) {
		G.transes.clear();
		msg.what=YJWMessage.SYNC_TRANS_SUCCESS.ordinal();
		for (int i=1;i<back.length;++i){
			Integer x = Integer.valueOf(back[i]);
			G.transes.add(x);			
		}
		}
		super.OnSuccess();
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_NULL_TRANS, YJWMessage.GET_DEAL_FAILED, "您还没有有关您的业务");

	}

}
