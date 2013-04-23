package com.app.yjw.thread;

import java.io.File;

import com.app.yjw.util.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class UpdateThread extends Thread{
	private Context context;
	private String updateURL;
	public UpdateThread(Context context,String updateURL){
		this.context=context;
		this.updateURL=updateURL;
		this.start();
	}
	
	public void run(){
		File file =Utility.downLoadFile(context,
				updateURL);
		Log.e("OpenFile", file.getName());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(
				Uri.fromFile(file),
				"application/vnd.android.package-archive");
		((Activity) context)
				.startActivity(intent);
	}

}
