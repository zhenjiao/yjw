package com.app.yjw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.app.yjw.thread.RegisterThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.Util;
import com.app.yjw.util.YJWMessage;

@Deprecated
public class ValidatePageActivity extends Activity implements OnClickListener {

	private Button next_button;
	private EditText code_edittext;
	private Button back_button;
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(YJWMessage.values()[msg.what])
			{
			case REGISTER_SUCCESS:
				Util.startNewActivity(ValidatePageActivity.this, InitSettingPageActivity.class, true);
				break;
			case REGISTER_FAILURE:
				Log.i("ValidatePageActivaty","failure");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.validation_page);
		next_button = (Button) findViewById(R.id.btn_validatecode);
		next_button.setOnClickListener(this);
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		code_edittext = (EditText) findViewById(R.id.editPassword);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private boolean checkAllInfoFilled()
	{
		return !code_edittext.getText().toString().equals("");
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.btn_validatecode && checkAllInfoFilled())
		{
			RegisterThread rt = new RegisterThread();
		//	rt.setValidateCode(code_edittext.getText().toString());
			//rt.setStep(RegisterStep.SendValidateCode);
			rt.setHandler(handler);
			//ThreadController.getInstance().addThread(rt);
			rt.start();
			//ThreadController.getInstance().RunSync();
		}else if(arg0.getId() == R.id.bt_left)
		{
			Util.startNewActivity(this, RegisterPageActivity.class, true);
		}
	}

}
