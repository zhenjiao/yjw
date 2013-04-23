package com.app.yjw;

import java.sql.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
//import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.thread.AddDealThread;
import com.app.yjw.thread.ShowMessageThread;

@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class AddDealPageActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * Members
	 */

	Button back_button;
	Button add_button;
	EditText title_edittext;
	DatePicker datepicker;
	TextView added_text;
	EditText refer_edittext;
	EditText commission_edittext;
	EditText details_edittext;
	Button add_contact_button;
	String[] phones;
	CheckBox confirm_checkbox;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Toast.makeText(AddDealPageActivity.this, "添加成功",
						Toast.LENGTH_SHORT).show();
				String str = (String) msg.obj;

				if (!str.equals("")) {
					if (str.contains(",")) {
						String[] phones = str.split(",");
						for (String p : phones) {
							sendSMS(p, generateSMSMessage());
						}
					} else {
						sendSMS(str, generateSMSMessage());
					}
				}

				AddDealPageActivity.this.finish();
			} else {
				Toast.makeText(AddDealPageActivity.this, "添加失败",
						Toast.LENGTH_SHORT).show();
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_deal_page);
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private boolean checkAllInfoFilled() {
		return editTextFilled(title_edittext) && editTextFilled(refer_edittext)
				&& editTextFilled(commission_edittext)
				&& editTextFilled(details_edittext) && phones != null
				&& phones.length != 0;
	}

	private boolean editTextFilled(EditText et) {
		return !et.getText().equals("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			if (checkAllInfoFilled()) {
				send();
			}
			else
				Toast.makeText(AddDealPageActivity.this, "信息不完整",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.add_contant:
			Intent intent = new Intent();
			intent.setClass(this, ContactsActivity.class);
			intent.putExtra("from", this.getClass().toString());
			startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			phones = (String[]) data.getSerializableExtra("phones");
			//added_text.setBackgroundColor(Color.BLACK);
				for (int i = 0; i < phones.length; ++i)
				//System.out.println(phones[i])
					if(i==0)
						added_text.setText(phones[i]);
					else
						added_text.setText(added_text.getText()+"\n"+phones[i]);
			break;
		default:
			Toast.makeText(this, "error on activity result", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	// ugly codes
	@SuppressLint({ "Hansk", "HandlerLeak", "HandlerLeak", "HandlerLeak" })
	private void send() {
		String title = title_edittext.getText().toString();
		Date date = new Date(datepicker.getYear(), datepicker.getMonth(),
				datepicker.getDayOfMonth());
		String refer = refer_edittext.getText().toString();
		String commission = commission_edittext.getText().toString();
		String detail = details_edittext.getText().toString();
		AddDealThread thread = new AddDealThread(YJWActivity.user.getSid(),
				title, "", refer, commission, date, detail, phones,
				confirm_checkbox.isChecked());
		thread.setHandler(handler);
		new Thread(thread).start();
	}

	protected void init() {
		super.init();
	}

	private void sendSMS(String phone, String text) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phone, null, text, null, null);
	}

	private String generateSMSMessage() {
		return "!!!!心生意累！！！！";
	}

	@Override
	protected void initViews() {
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		add_button = (Button) findViewById(R.id.bt_right);
		add_button.setOnClickListener(this);
		title_edittext = (EditText) findViewById(R.id.et_title);
		datepicker = (DatePicker) findViewById(R.id.dp_expire_date);
		refer_edittext = (EditText) findViewById(R.id.et_refer_fee);
		commission_edittext = (EditText) findViewById(R.id.et_commission_fee);
		details_edittext = (EditText) findViewById(R.id.et_details);
		add_contact_button = (Button) findViewById(R.id.add_contant);
		add_contact_button.setOnClickListener(this);
		confirm_checkbox = (CheckBox) findViewById(R.id.cb_confirm);
		added_text=(TextView) findViewById(R.id.tv_added);
	}

}
