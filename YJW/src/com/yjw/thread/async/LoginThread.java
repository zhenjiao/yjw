package com.yjw.thread.async;

import com.yjw.bean.Bean;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.BeanPacker;
import com.yjw.util.ErrorCode;
import com.yjw.util.YJWMessage;

public class LoginThread extends YJWBaseThread {
	
	@Override
	protected String generateURL() {
		return NetworkConstants.URL_LOGIN;
	}
	
	@Override
	protected void init() {
		RegisterError(ErrorCode.E_LOGIN_FAILED, YJWMessage.LOGIN_FAILED, "�û������������");		
	}
	
	@Override
	protected void OnSuccess() {
		msg.what = YJWMessage.LOGIN_SUCCESS.ordinal();
		msg.obj=Bean.Pack(back[1]);
		super.OnSuccess();
	}
}
