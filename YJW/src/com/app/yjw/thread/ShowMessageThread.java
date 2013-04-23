package com.app.yjw.thread;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.app.yjw.net.NetworkFactory;
import com.app.yjw.util.YJWMessage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ShowMessageThread extends YJWBaseThread {
	private static ShowMessageThread instance = new ShowMessageThread();
	public static Handler mHandler;
	private static Context currentContext = null;
	private static String ConnectErrorMessage = "网络连接失败，请稍后再试";
	private static String ReconnectMessage = "网络连接失败，稍后重新连接";
	private ShowMessageThread(){
		
	}
	public static synchronized ShowMessageThread GetInstance(){
		if(instance == null)
			instance = new ShowMessageThread();
		return instance;
	}
	public static void SetCurrentContext(Context c){
		currentContext = c;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare(); 
		mHandler = new Handler() { 
			private long time = 0;			//防止多个重连消息重复显示
			public void handleMessage(Message msg) {
				//Log.d("Message", "Get");
				switch(msg.what){
					case YJWMessage.NET_FAIL_RECONNECT:
						try{
							long timenow =System.currentTimeMillis(); 
							if(time ==0||timenow-time>=NetworkFactory.ReconnectTime){
								Toast.makeText(currentContext, ReconnectMessage, Toast.LENGTH_SHORT).show();
								time = timenow;
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						//Log.d("Message", "Reconnect");
						break;
					case YJWMessage.NET_FAIL_NORECONNECT:
						try{
							Toast.makeText(currentContext, ConnectErrorMessage, Toast.LENGTH_SHORT).show();
						}catch(Exception e){
							e.printStackTrace();
						}
						//Log.d("Message", "Reconnect");
						break;
				}
				// process incoming messages here 
				super.handleMessage(msg);
			} 
		}; 
		Looper.loop();
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String generateURL() {
		// TODO Auto-generated method stub
		return null;
	}

}
