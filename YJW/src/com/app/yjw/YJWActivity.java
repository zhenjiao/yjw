package com.app.yjw;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.app.yjw.database.DBProxy;
import com.app.yjw.database.DBStatic;
import com.app.yjw.thread.LoginThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.Util;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.AccountBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;

public class YJWActivity extends Activity implements OnClickListener {
	public static ShowMessageThread BackgroundThread;
	public static SQLiteDatabase database;
	public static UserBean user;
	private static YJWActivity instance;
	private static YJWActivity getInstance(){return instance;}
	
	static Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch(YJWMessage.values()[msg.what]){
			case LOGIN_SUCCESS:{
				Toast.makeText(getInstance(), "登录成功", Toast.LENGTH_SHORT).show();
				DBProxy.clearAccountTable();
				UserBean bean = (UserBean)msg.obj;
				user = bean;
				DBProxy.insertNewAccount((AccountBean)new BeanPacker(bean).transTo(AccountBean.class));
				//Util.startNewActivity(getInstance(), MainPageActivity.class, true);
				Util.startNewActivity(getInstance(), TestActivity.class, true);
			}break;
			default:
				Toast.makeText(getInstance(), (String)msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		instance = this;
		
		//检测网络连接用，部分手机不能自动连接网络
		/*if(!check_NetworkInfo()){
			startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}*/
		
		database = this.openOrCreateDatabase("YJW.db", MODE_PRIVATE, null);
		dropAllTable();
		database.execSQL(DBStatic.CreateTableByBean(AccountBean.class, DBStatic.AccountTableName));
		database.execSQL(DBStatic.CreateTableByBean(DealBean.class, DBStatic.DealTableName));
		database.execSQL(DBStatic.CreateTableByBean(UserBean.class, DBStatic.UserTableName));
		database.execSQL(DBStatic.CreateTableByBean(TransBean.class, DBStatic.TransTableName));
		database.execSQL(DBStatic.CreateMessageTable);		
		//SmsManager.getDefault().sendTextMessage("13816955910", null, "这是短信", null, null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		init();
		
		if (check_database()) {
			LoginThread lt=new LoginThread();
			lt.setBean(new BeanPacker(user).transTo(AccountBean.class));
			lt.setHandler(handler);
			lt.start();
		}
		//初始化后台消息提示
		BackgroundThread=ShowMessageThread.GetInstance();
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
		Cursor cur = database.query(DBStatic.AccountTableName, null, null, null, null, null, null);
		if (cur != null && 0 != cur.getCount()) {
			cur.moveToFirst();
			YJWActivity.user = new UserBean();
			YJWActivity.user.setCellphone(cur.getString(0));
			YJWActivity.user.setPassword(cur.getString(1));
			Log.i("YJWActivity","tel:" + user.getCellphone() + ",pw:" + user.getPassword());
		} else {
			// do something
			Toast.makeText(this, "没有账户", Toast.LENGTH_SHORT).show();
			cur.close();
			return false;
		}
		cur.close();
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rigister_button) {
			Util.startNewActivity(this, RegisterPageActivity.class, true);
		} else if (v.getId() == R.id.login_button) {
			Util.startNewActivity(this, LoginPageActivity.class, true);
		}
	}
	
	private void dropAllTable(){
		//database.execSQL("DROP TABLE IF EXISTS "+DBStatic.AccountTableName);
		database.execSQL("DROP TABLE IF EXISTS "+DBStatic.MessageTableName);
		database.execSQL("DROP TABLE IF EXISTS "+DBStatic.DealTableName);
		database.execSQL("DROP TABLE IF EXISTS "+DBStatic.UserTableName);
		database.execSQL("DROP TABLE IF EXISTS "+DBStatic.TransTableName);
		
	}
	
}