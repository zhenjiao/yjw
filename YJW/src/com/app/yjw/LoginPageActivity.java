package com.app.yjw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yjw.database.DBProxy;
import com.app.yjw.thread.LoginThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.Util;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.AccountBean;
import com.yjw.bean.UserBean;


public class LoginPageActivity extends Activity implements OnClickListener {

	Button back_button;
	Button login_button;
	EditText username_edittext;
	EditText password_edittext;
	static String phone;
	static String password;
	static private LoginPageActivity instance;
	
	static public LoginPageActivity getInstance() {
		return instance;
	}

	static private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(YJWMessage.values()[msg.what]){
			case LOGIN_SUCCESS:{
				Toast.makeText(getInstance(), "登录成功", Toast.LENGTH_SHORT).show();
				DBProxy.clearAccountTable();
				UserBean bean = (UserBean)msg.obj;	
				YJWActivity.user = bean;
				DBProxy.insertNewAccount((AccountBean)new BeanPacker(bean).transTo(AccountBean.class));
				Util.startNewActivity(getInstance(), TestActivity.class, true);
			}break;
			default:
				Toast.makeText(getInstance(), (String)msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			//super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_page);
		instance=this;
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private void init() {
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);

		login_button = (Button) findViewById(R.id.bt_right);
		login_button.setOnClickListener(this);

		username_edittext = (EditText) findViewById(R.id.username_edittext);
		password_edittext = (EditText) findViewById(R.id.password_edittext);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_left) {
			Util.startNewActivity(this, YJWActivity.class, true);
			this.finish();
		}
		if (v.getId() == R.id.bt_right) {
			// check

			phone = username_edittext.getText().toString();
			password = password_edittext.getText().toString();
			if (phone.length()!=11 || password.length()<6) {
				Toast.makeText(this,"请填写正确的登录信息", Toast.LENGTH_SHORT).show();
				return;
			}
			AccountBean bean=new AccountBean();
			bean.setCellphone(phone);
			bean.setPassword(password);
			LoginThread lt=new LoginThread();
			lt.setBean(bean);
			lt.setHandler(handler);
			lt.start();
		}
	}
	
}
