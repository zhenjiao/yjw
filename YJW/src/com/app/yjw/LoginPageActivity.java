package com.app.yjw;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yjw.database.DBStatic;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.net.NetworkFactory;
import com.app.yjw.pojo.UserInfo;
import com.app.yjw.thread.ShowMessageThread;


public class LoginPageActivity extends Activity implements OnClickListener {

	Button back_button;
	Button login_button;
	EditText username_edittext;
	EditText password_edittext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_page);
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
			Intent intent = new Intent();
			intent.setClass(this, YJWActivity.class);
			startActivity(intent);
			this.finish();
		}
		if (v.getId() == R.id.bt_right) {
			// check

			String phone = username_edittext.getText().toString();
			String password = password_edittext.getText().toString();
			if (phone.equals("") || password.equals(""))
				return;
			List<BasicNameValuePair> parameters = new LinkedList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("cellphone", phone));
			parameters.add(new BasicNameValuePair("password", password));
			String str = NetworkFactory.getInstance().doPost(
					NetworkConstants.URL_LOGIN, parameters,false);
			//str="4313344070361043716668";
			System.out.print(str);
			if (str== null){
				return;
			}
			else if (str.equals("该账户不存在") || str.equals("")) {
				Toast.makeText(this, "该账户不存在", Toast.LENGTH_SHORT).show();
				return;
			}

			YJWActivity.database
					.execSQL(DBStatic.generateSQLForDeleteAccount());
			YJWActivity.user = new UserInfo();
			YJWActivity.user.setSid(str);
			YJWActivity.user.setPhoneNumber(phone);
			ContentValues cv = new ContentValues();
			cv.put("phone", phone);
			cv.put("sid", str);
			YJWActivity.database.insert(DBStatic.AccountTableName, null, cv);
			Intent intent = new Intent();
			intent.setClass(this, MainPageActivity.class);
			startActivity(intent);
			this.finish();
		}
	}

}
