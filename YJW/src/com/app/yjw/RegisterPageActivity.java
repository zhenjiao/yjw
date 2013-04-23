package com.app.yjw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.app.yjw.thread.RegisterThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.RegisterThread.RegisterStep;
import com.app.yjw.thread.ThreadController;
import com.app.yjw.util.Utility;
import com.app.yjw.util.YJWMessage;

public class RegisterPageActivity extends Activity implements OnClickListener {

	Button back_button;
	Button register_button;
	CheckBox follow_checkbox;
	EditText phone_edittext;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YJWMessage.REGISTER_SUCCESS:
				Utility.startNewActivity(RegisterPageActivity.this,
						ValidatePageActivity.class, true);
				break;
			case YJWMessage.REGISTER_FAILURE:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	@Override
	public void onBackPressed() {
		Log.d("onBackPressed", "pressed");
		Intent intent = new Intent();
		intent.setClass(this, YJWActivity.class);
		startActivity(intent);
		this.finish();
	}
	private void init() {
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);

		register_button = (Button) findViewById(R.id.bt_right);
		register_button.setOnClickListener(this);

		follow_checkbox = (CheckBox) findViewById(R.id.checkBox1);
		phone_edittext = (EditText) findViewById(R.id.edittext_phonenumber);
	}

	private boolean checkAllInfoFilled() {
		if (follow_checkbox.isChecked() && !phone_edittext.getText().equals(""))
			return true;
		Toast.makeText(this, "«ÎÃÓ–¥ÕÍ’˚°£", Toast.LENGTH_SHORT).show();
		return false;
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
			if (checkAllInfoFilled()) {
				String phone = phone_edittext.getText().toString();

				RegisterThread t = (RegisterThread) ThreadController
						.getInstance().fetchThread(RegisterThread.class);
				if (t != null) {
					ThreadController.getInstance().removeThread(t);
				}

				t = new RegisterThread();					
				t.setStep(RegisterStep.Register);
				t.setPhoneNumber(phone);
				t.setHandler(handler);

				ThreadController.getInstance().addThread(t);
				t.start();

//				ThreadController.getInstance().RunAsync();

			}
		}
	}
}
