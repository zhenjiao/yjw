package com.app.yjw.thread;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.YJWMessage;

public class ConfTransThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CONFTRANS;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_CONF_TRANS_ERROR,YJWMessage.CONF_TRANS_FAILED,"��֤ʧ��");
		RegisterError(ErrorCode.E_NOT_ENOUGH_BALANCE,YJWMessage.CONF_TRANS_NOT_ENOUGH_BALANCE,"û���㹻�����");
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.CONF_TRANS_SUCCESS.ordinal();
		super.OnSuccess();
	}

}
