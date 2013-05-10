/*
 * 
 *     Created by 亦恒 陶 
 *  Copyright (c) 2012年 . All rights reserved.
 *  
 */
package com.app.yjw.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.os.Message;
import android.util.Log;

import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.YJWMessage;

public class NetworkFactory {
	public static NetworkFactory instance = null;
	public static final int ReconnectTime = 20000;	//重连时间：20秒
	public static synchronized NetworkFactory getInstance() {
		if (instance == null) {
			instance = new NetworkFactory();
		}
		return instance;
	}

	public String doGet(List<BasicNameValuePair> parameters) {
		return doGet(NetworkConstants.SERVER_URL, parameters);
	}

	private HttpClient getDefaultHttpClient() {
		HttpClient httpClient;
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,	3000);
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		return httpClient;
	}

	public String doGet(String basicURL, List<BasicNameValuePair> parameters) {

		String responseText = "";
		String uri = basicURL + "?";

		if (parameters != null) {
			for (BasicNameValuePair pair : parameters) {
				uri += pair.getName() + "=" + pair.getValue() + "&";
			}
		}

		HttpGet get = new HttpGet(uri);
		HttpResponse response;
		try {
			response = this.getDefaultHttpClient().execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream iStream = response.getEntity().getContent();
				BufferedReader bfReader = new BufferedReader(
						new InputStreamReader(iStream));
				String str;
				while ((str = bfReader.readLine()) != null) {
					responseText = responseText + str;
				}
			} else {
				// TODO 收到的状态码有错误
				Log.e("NetworkFactory", "收到的状态码有错误");
			}
		} catch (ConnectTimeoutException cte) {
			cte.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return responseText;
	}

	public String doPost(List<BasicNameValuePair> parameters) {
		return doPost(NetworkConstants.SERVER_URL, parameters);
	}

	private String doPost(String basicURL, List<BasicNameValuePair> parameters) {
		String responseText = "";
		Log.i("Do Post URL", basicURL + " with " + parameters.size() + " parameters:"+parameters);

		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(parameters, "UTF8");
			HttpPost post = new HttpPost(basicURL);
			post.setEntity(httpentity);
			HttpResponse response = this.getDefaultHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// TODO 解析字符串
				// alreadyGotResponse = true;
				InputStream iStream = response.getEntity().getContent();
				BufferedReader bfReader = new BufferedReader(new InputStreamReader(iStream));
				String str;
				while ((str = bfReader.readLine()) != null) {
					responseText = responseText + str;
				}
				Log.i("Response From Post", responseText);
			} else {
				// TODO 收到的状态码有错误
				Log.i("doPost", "error status code"	+ response.getStatusLine().getStatusCode());
			}
		} catch(Exception e){
			return null;
		}
		/*catch (HttpResponseException hre) {
			hre.printStackTrace();
		} catch (ConnectTimeoutException cte) {
			cte.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}*/
		return responseText;
	}
	
	private Object doPostObject(String basicURL,
			List<BasicNameValuePair> parameters) {
		Object obj = null;
		Log.i("Do Post URL", basicURL + " with " + parameters.size()
				+ " parameters:"+parameters);
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(parameters, "UTF8");
			HttpPost post = new HttpPost(basicURL);
			post.setEntity(httpentity);
			HttpResponse response = this.getDefaultHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// TODO 解析字符串
				InputStream iStream = response.getEntity().getContent();
				ObjectInputStream oos2 = new ObjectInputStream(iStream);
				obj = oos2.readObject();
			} else {
				// TODO 收到的状态码有错误
				Log.e("doPost", "error status code"
						+ response.getStatusLine().getStatusCode());
			}
		} catch(Exception e){
			return null;
		} 
		/*catch (HttpResponseException hre) {
			hre.printStackTrace();
		} catch (ConnectTimeoutException cte) {
			cte.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}*/
		return obj;
	}
	public String doPost(String basicURL, List<BasicNameValuePair> parameters,boolean flag){
		//flag:是否重连
		String str = doPost(basicURL,parameters);
		if(str==null){
			if(flag){
				
				while(str==null){
					Message msg = Message.obtain();
					msg.what = YJWMessage.NET_FAIL_RECONNECT.ordinal();
					ShowMessageThread.mHandler.sendMessage(msg);
					try {
						Thread.sleep(NetworkFactory.ReconnectTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					str = doPost(basicURL,parameters);
				}
			}
			else {
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
		}
		return str;
	}
	public Object doPostObject(String basicURL,
			List<BasicNameValuePair> parameters,boolean flag){
		Object obj = null;
		obj = doPostObject(basicURL,parameters);
		if(obj==null){

			if(flag){
				while(obj==null){	
					Message msg = Message.obtain();
					msg.what = YJWMessage.NET_FAIL_RECONNECT.ordinal();
					ShowMessageThread.mHandler.sendMessage(msg);
					try {
						Thread.sleep(NetworkFactory.ReconnectTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					obj = doPostObject(basicURL,parameters);
				}
			}
			else {
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
		}
		return obj;
	}
}
