package com.app.yjw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.app.yjw.thread.RegisterThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.YJWMessage;

@Deprecated
public class InitSettingPageActivity extends Activity implements
		OnClickListener {

	EditText name_edittext;
	EditText pwd_edittext;
	Button back_button;
	Button next_button;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (YJWMessage.values()[msg.what]) {
			case REGISTER_SUCCESS:
			/*	// insert into database
				RegisterThread rt =new RegisterThread();
				String phone = rt.getPhoneNumber();
				String sid = rt.getSid();
				rt.setStep(RegisterThread.RegisterStep.RegisterFinished);
				rt.setHandler(null);
				rt.start();
				DBProxy.insertNewAccount(phone, sid);
				YJWActivity.user = new UserInfo();
				YJWActivity.user.setSid(sid);
				YJWActivity.user.setPhoneNumber(phone);
				Utility.startNewActivity(InitSettingPageActivity.this, MainPageActivity.class, true);
				break;*/
			case REGISTER_FAILURE:
				// error;
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init_setting);
		name_edittext = (EditText) findViewById(R.id.et_name);
		pwd_edittext = (EditText) findViewById(R.id.et_password);
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		next_button = (Button) findViewById(R.id.bt_right);
		next_button.setOnClickListener(this);
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private boolean checkAllInfoFilled() {
		return !name_edittext.getText().toString().equals("")
				&& !pwd_edittext.getText().toString().equals("");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_left) {
			this.finish();
		} else if (v.getId() == R.id.bt_right && checkAllInfoFilled()) {
			RegisterThread rt = new RegisterThread();
			//RegisterThread rt = new RegisterThread();
		/*	rt.setPassword(pwd_edittext.getText().toString());
			rt.setRealname(name_edittext.getText().toString());
			rt.setStep(RegisterStep.FinishRegister);*/
			rt.setHandler(handler);
			//rt.start();
			rt.start();
		}
	}

}
