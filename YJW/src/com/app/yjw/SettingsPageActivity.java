package com.app.yjw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.database.DBStatic;
import com.app.yjw.pojo.UserInfo;
import com.app.yjw.thread.FetchBalanceThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.ThreadController;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.Balance;

public class SettingsPageActivity extends BaseTabActivity implements
		OnClickListener {

	Button logout_button;
	TextView tv_balance;
	Balance balance;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YJWMessage.FETCH_BALANCE_SUCCESS:
				balance = (Balance) msg.obj;
				tv_balance.setText(new Float(balance.Balance).toString());
				break;
			case YJWMessage.FETCH_BALANCE_FAILURE:
				Toast.makeText(SettingsPageActivity.this, "��ȡ�����ִ���",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_page);
		init();
		fetchBalance();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private void fetchBalance() {
		FetchBalanceThread fbt = new FetchBalanceThread();
		fbt.setHandler(this.handler);
		ThreadController.getInstance().addThread(fbt);
		ThreadController.getInstance().runAndRemove();
	}

	private void init() {
		logout_button = (Button) findViewById(R.id.bt_left);
		logout_button.setOnClickListener(this);
		tv_balance = (TextView) findViewById(R.id.balance);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return getParent().onKeyDown(keyCode, event);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			YJWActivity.database
					.execSQL(DBStatic.generateSQLForDeleteAccount());
			YJWActivity.user = new UserInfo();
			Intent intent = new Intent();
			intent.setClass(this, YJWActivity.class);
			startActivity(intent);
			this.finish();
			break;
		}
	}

}