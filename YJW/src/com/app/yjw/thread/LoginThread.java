package com.app.yjw.thread;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.YJWMessage;

public class LoginThread extends YJWBaseThread {
	
	@Override
	protected String generateURL() {
		return NetworkConstants.URL_LOGIN;
	}
	
	@Override
	protected void init() {
		RegisterError(ErrorCode.E_LOGIN_FAILED, YJWMessage.LOGIN_FAILED, "用户名或密码错误");		
	}
	
	@Override
	protected void OnSuccess() {
		msg.what = YJWMessage.LOGIN_SUCCESS.ordinal();
		msg.obj=new BeanPacker(back[1]).getBean();
		super.OnSuccess();
	}
}
