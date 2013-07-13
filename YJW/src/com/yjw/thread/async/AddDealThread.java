package com.yjw.thread.async;

import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.ErrorCode;
import com.yjw.util.YJWMessage;

public class AddDealThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_ADDDEAL;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_ADD_DEAL_FAILED, YJWMessage.ADD_DEAL_FAILED,"���ҵ��ʧ��");
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.ADD_DEAL_SUCCESS.ordinal();
		msg.obj="���ҵ��ɹ�";
		msg.arg2=ControlCode.C_TOAST_SHORT.ordinal();
		super.OnSuccess();
	}

}
