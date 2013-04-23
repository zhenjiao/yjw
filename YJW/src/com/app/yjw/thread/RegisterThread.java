package com.app.yjw.thread;

import java.util.ArrayList;
import android.util.Log;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Looper;
import android.os.Message;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.util.YJWMessage;

public class RegisterThread extends YJWBaseThread {

	public enum RegisterStep {
		Register, SendValidateCode, ResendValidateCode, FinishRegister
	}

	private RegisterStep currentStep = RegisterStep.Register;
	private String phoneNumber;
	private String realname;
	private String password;
	private String validateCode;
	private String sid;

	public void setStep(RegisterStep step) {
		this.currentStep = step;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String name) {
		this.realname = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public void run() {
		String backStr = NetworkFactory.getInstance().doPost(generateURL(), generateParameters(),false);
		//String backStr = NetworkFactory.getInstance().doGet(generateURL(), generateParameters());
		
		if(backStr!=null){
			Log.d("result of registration = ", backStr);
			Message msg = Message.obtain();
			switch(currentStep){
			case Register:
				sid = backStr.substring(0, backStr.indexOf(","));
				msg.what = YJWMessage.REGISTER_SUCCESS;
				break;
			case SendValidateCode:
				if(backStr.equals("fail"))
				{
					msg.what = YJWMessage.REGISTER_FAILURE;
				}
				else
				{
					msg.what = YJWMessage.REGISTER_SUCCESS;
				}
				break;
			case FinishRegister:
				msg.what = YJWMessage.REGISTER_SUCCESS;
				break;
			}
			handler.sendMessage(msg);
		}
		else
		{
			Log.d("Trace", "result of registration is null");
		}
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		switch (currentStep) {
		case Register:
			parameters.add(new BasicNameValuePair("cellphone", phoneNumber));
			break;
		case SendValidateCode:
			parameters.add(new BasicNameValuePair("sid", sid));
			parameters
					.add(new BasicNameValuePair("validateCode", validateCode));
			break;
		case ResendValidateCode:
			break;
		case FinishRegister:
			parameters.add(new BasicNameValuePair("sid", sid));
			parameters.add(new BasicNameValuePair("password", password));
			parameters.add(new BasicNameValuePair("name", realname));
			break;
		}
		return parameters;
	}

	@Override
	protected String generateURL() {
		String url = null;
		switch (currentStep) {
		case Register:
			url = NetworkConstants.URL_REGISTER;
			break;
		case SendValidateCode:
			url = NetworkConstants.URL_REGISTERVALIDATE;
			break;
		case ResendValidateCode:
			url = NetworkConstants.URL_RESENDVALIDATE;
			break;
		case FinishRegister:
			url = NetworkConstants.URL_FINISHREGISTER;
			break;
		}
		return url;
	}

}
