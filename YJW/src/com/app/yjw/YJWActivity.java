package com.app.yjw;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.app.yjw.database.DBStatic;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.UserInfo;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.ThreadController;
import com.app.yjw.util.Utility;
import com.yjw.bean.Version;

public class YJWActivity extends Activity implements OnClickListener {
	public static Thread BackgroundThread = new Thread(ShowMessageThread.GetInstance());
	public static SQLiteDatabase database;
	public static UserInfo user;

	@Override
	public void onCreate(Bundle savedInstanceState) {  
		
		//检测网络连接用，部分手机不能自动连接网络
		/*if(!check_NetworkInfo()){
			startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}*/
		
		Log.d("msg", "onCreate");
		
		//System.out.println("bf");
		database = this.openOrCreateDatabase("YJW.db", MODE_PRIVATE, null);
		//System.out.println("af");
		
		//用三个try确保三个数据库都已建立
		try{
		database.execSQL(DBStatic.CreateAccountTable);
		}catch (Exception e) {
			//e.printStackTrace();
		}
		try{
		database.execSQL(DBStatic.CreateMessageTable);
		}catch (Exception e) {
			//e.printStackTrace();
		}
		try {
			database.execSQL(DBStatic.CreateDealTable);	
			// this.deleteDatabase(DBStatic.AccountTableName);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		
		super.onCreate(savedInstanceState);
		//super.onCreate(null);
		Log.d("msg", "after super.onCreate");
		
		setContentView(R.layout.main);
		Log.d("msg", "after UI");
		
		init();
		Log.d("msg", "after init");
	
//		Uri uri = Uri.parse("");
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);	
		
/*		
		// Just for test
		if (Utility.TEST_MODE) {
			// TODO dosometing

			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			
			Version obj = (Version) NetworkFactory.getInstance().doPostObject(
					"http://192.168.1.110:8080/YjwServer/OutputVersionAction",
					"http://sselab.tongji.edu.cn/YjwServer/OutputVersionAction",
					parameters,false);
			System.out.println("OBJECT: " + obj.UpdateURL);
			
			return;
		}
*/
		
/*
		if (check_database()) {
			Utility.startNewActivity(this, MainPageActivity.class, true);
		}*/
		//初始化后台消息提示
			
		ThreadController.getInstance().addThread(BackgroundThread);
		if (!BackgroundThread.isAlive())
			BackgroundThread.start();

	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}

	private void init() {
		Button register_button = (Button) findViewById(R.id.rigister_button);
		register_button.setOnClickListener(this);
		Button login_button = (Button) findViewById(R.id.login_button);
		login_button.setOnClickListener(this);
	}

	private boolean check_NetworkInfo()
    {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        //wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        
        //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
        if(mobile==State.CONNECTED||mobile==State.CONNECTING)
            return true;
        if(wifi==State.CONNECTED||wifi==State.CONNECTING)
            return true;
        
        //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //进入手机中的wifi网络设置界面
        return false;
    }
	
	private boolean check_database() {
		Cursor cur = database.query(DBStatic.AccountTableName,
				DBStatic.AccountTableColumns, null, null, null, null, null);
		if (cur != null && 0 != cur.getCount()) {
			cur.moveToFirst();
			YJWActivity.user = new UserInfo();
			// YJWActivity.user.setId(cur.getInt(0));
			YJWActivity.user.setPhoneNumber(cur.getString(1));
			YJWActivity.user.setSid(cur.getString(2));
			System.out.println("id:" + user.getId() + "sid:" + user.getSid());
		} else {
			// do something
			Toast.makeText(this, "没有账户", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rigister_button) {
			Intent intent = new Intent();
			intent.setClass(this, RegisterPageActivity.class);
			startActivity(intent);
			this.finish();
		} else if (v.getId() == R.id.login_button) {
			Intent intent = new Intent();
			intent.setClass(this, LoginPageActivity.class);
			startActivity(intent);
			this.finish();
		}
	}
	
	
}