package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.YJWMessage;

public class DelTransThread extends YJWBaseThread {

	private Integer id;
	public void setId(Integer id){this.id=id;}
	

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_DELTRANS;
	}
	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("id",id.toString()));
		return list;
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.DEL_TRANS_SUCCESS.ordinal();
		super.OnSuccess();
	}


	@Override
	protected void init() {
		RegisterError(ErrorCode.E_DEL_TRANS_FAILED,YJWMessage.DEL_TRANS_FAILED,"ɾ������ʧ��");		
	}

}