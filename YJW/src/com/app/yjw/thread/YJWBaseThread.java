/*
 * Created By Yiheng Tao
 * 
 * YJWBaseThread.java
 * 
 * This is the abstract and base class of the threads using in this application
 * There is a handler should used for sending message to activities
 * Each children class should override the run function and set its own Handler
 * call SendMessage when sending message
 */

package com.app.yjw.thread;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

public abstract class YJWBaseThread extends Thread {

	/*
	 * handler
	 * 
	 * @important : init/fill it before using!
	 */
	protected Handler handler;

	/*
	 * Function Name : SetHandler
	 * 
	 * @param : Handler
	 */
	public void setHandler(Handler h) {
		this.handler = h;
	}

	/*
	 * Function Name : SendMessage
	 * 
	 * @param : Message
	 * 
	 * @important : msg should not be null
	 */
	protected void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}
	
	/**
	 * 
	 */
	public enum ThreadState{
		ThreadNotStarted, ThreadRunning, ThreadFinished
	}

	@Override
	public abstract void run();

	/*
	 * Function Name : GenerateParameters
	 * 
	 * @Abstract
	 */
	protected abstract List<BasicNameValuePair> generateParameters();
	protected abstract String generateURL();
}
