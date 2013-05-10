package com.app.yjw.ctrl;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.Toast;

import com.app.yjw.thread.TemplateThread;
import com.app.yjw.thread.YJWBaseThread;
import com.app.yjw.util.ErrorCode;
import com.app.yjw.util.YJWMessage;

public class YJWControler {
	
	private static YJWControler instance = null;
	private static Handler handler;
	private Activity activity;
	private YJWBaseThread thread;
	private Map<YJWMessage,Callback> map;
	
	public static Handler getHandler(){
		return handler;
	}
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public static YJWControler getInstance(){
		if (instance==null) instance = new YJWControler();
		return instance;
	}
	
	public YJWControler() {
		handler = new Handler(callback);
		map = new HashMap<YJWMessage,Callback>();
	}
	
	private Callback callback = new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			callback=map.get(YJWMessage.values()[msg.what]);
			if (callback==null){
				if (msg.obj==null||activity==null)	return false;
				switch(ControlCode.values()[msg.arg2]){
				case C_TOAST_SHORT:Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_SHORT).show();break;
				case C_TOAST_LONG :Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_LONG ).show();break;
				}
				return true;
			}
			callback.handleMessage(msg);
			return true;
		}
	};
	
	public static void registerCallback(YJWMessage msg,Callback callback){
		getInstance().map.put(msg, callback);
	}
	public static void unregisterCallback(YJWMessage msg){
		getInstance().map.remove(msg);
	}
	
	public YJWBaseThread start(Class<? extends YJWBaseThread> type, Object bean){
		try {
			thread=type.newInstance();
			thread.setBean(bean);
			thread.setHandler(handler);
			//Log.d("Thread",thread+" ready");
			thread.start();
		} catch (InstantiationException e) {
			e.printStackTrace();
			thread = null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			thread = null;
		}
		//Log.d("Thread",thread+" run");
		return thread;
	}
	
	public YJWBaseThread startTemplate(String url,Object bean,YJWMessage msgSuccess,YJWMessage msgError,ErrorCode error,String toast){
		thread=new TemplateThread();
		thread.setBean(bean);
		thread.setHandler(handler);
		((TemplateThread)thread).url=url;
		((TemplateThread)thread).successmessage=msgSuccess;
		((TemplateThread)thread).errormessage=msgError;
		((TemplateThread)thread).errorCode=error;
		((TemplateThread)thread).errorStr=toast;
		thread.start();
		return thread;
	}
	
	public YJWBaseThread startTemplate(String url,Object bean,YJWMessage msgSuccess){
		return startTemplate(url, bean, msgSuccess,null,null,null);
	}
	
	public static YJWBaseThread StartTemplate(String url,Object bean,YJWMessage msgSuccess,YJWMessage msgError,ErrorCode error,String toast){
		return getInstance().startTemplate(url, bean, msgSuccess, msgError, error, toast);
	}
	
	public static YJWBaseThread StartTemplate(String url,Object bean,YJWMessage msgSuccess){
		return getInstance().startTemplate(url, bean, msgSuccess);
	}
		
	public static YJWBaseThread Start(Class<? extends YJWBaseThread> type, Object bean){
		return getInstance().start(type, bean);
	}
	
}
