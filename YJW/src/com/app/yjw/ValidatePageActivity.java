package com.app.yjw;

import android.app.Activity;
import android.content.Intent;
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
import com.app.yjw.thread.RegisterThread.RegisterStep;
import com.app.yjw.thread.ThreadController;
import com.app.yjw.util.Utility;
import com.app.yjw.util.YJWMessage;

public class ValidatePageActivity extends Activity implements OnClickListener {

	private Button next_button;
	private EditText code_edittext;
	private Button back_button;
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case YJWMessage.REGISTER_SUCCESS:
				Utility.startNewActivity(ValidatePageActivity.this, InitSettingPageActivity.class, true);
				break;
			case YJWMessage.REGISTER_FAILURE:
				System.out.println("failure");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.validation_page);
		next_button = (Button) findViewById(R.id.button1);
		next_button.setOnClickListener(this);
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		code_edittext = (EditText) findViewById(R.id.editText1);
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
	public void onBackPressed() {
		Log.d("onBackPressed", "pressed inside Validation");
		Utility.startNewActivity(this, RegisterPageActivity.class, true);
	}
	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button1 && checkAllInfoFilled())
		{
			// run thread 
			RegisterThread rt = (RegisterThread) ThreadController
					.getInstance().fetchThread(RegisterThread.class);
			rt.setValidateCode(code_edittext.getText().toString());
			rt.setStep(RegisterStep.SendValidateCode);
			rt.setHandler(handler);
			rt.run();
//			ThreadController.getInstance().RunAsync();
			
			Intent intent = new Intent();
			intent.setClass(this, MainPageActivity.class);
			startActivity(intent);
			this.finish();
		}else if(arg0.getId() == R.id.bt_left)
		{
		
			Utility.startNewActivity(this, RegisterPageActivity.class, true);
		}
	}

}
