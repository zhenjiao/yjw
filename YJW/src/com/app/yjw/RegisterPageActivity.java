package com.app.yjw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.database.DBProxy;
import com.app.yjw.thread.LoginThread;
import com.app.yjw.thread.RegisterThread;
import com.app.yjw.thread.RegisterThread.RegisterStep;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.Util;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.AccountBean;
import com.yjw.bean.RegisterBean;
import com.yjw.bean.UserBean;

public class RegisterPageActivity extends Activity implements OnClickListener {

	private Button btn_back;
	private Button btn_next;
	private Button btn_getcode;
	private EditText et_phone;
	private EditText et_code;
	private EditText et_password;
	private EditText et_confirm;
	private EditText et_name;
	private EditText et_email;
	private CheckBox cb_follow;
	private TextView tv_privacy;
	private String sid;
	
	private static RegisterPageActivity instance;
	static RegisterPageActivity getInstance(){return instance;}

	static private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (YJWMessage.values()[msg.what]) {
			case REGISTER_SUCCESS:{		
				AccountBean bean = new AccountBean();
				bean.setCellphone(getInstance().et_phone.getText().toString());
				bean.setPassword(getInstance().et_password.getText().toString());
				LoginThread lt= new LoginThread();
				lt.setBean(bean);
				lt.setHandler(this);
				lt.start();
			}break;
			case REGISTER_FAILURE:{
				Toast.makeText(getInstance(), (String)msg.obj, Toast.LENGTH_SHORT).show();
			}break;
			case REGISTER_GETVALIDATECODE:{
				getInstance().sid = (String)msg.obj;
			}break;
			case LOGIN_SUCCESS:{
				Toast.makeText(getInstance(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
				DBProxy.clearAccountTable();
				UserBean bean = (UserBean)msg.obj;		
				YJWActivity.user = bean;
				DBProxy.insertNewAccount((AccountBean)new BeanPacker(bean).transTo(AccountBean.class));
				Util.startNewActivity(getInstance(), TestActivity.class, true);
			}break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		instance=this;
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private void init() {
		btn_back = (Button)findViewById(R.id.bt_left);
		btn_back.setOnClickListener(this);
		btn_next = (Button)findViewById(R.id.bt_right);
		btn_next.setOnClickListener(this);		
		btn_getcode = (Button)findViewById(R.id.btn_validatecode);
		btn_getcode.setOnClickListener(this);		
		
		et_code = 		(EditText)findViewById(R.id.editValidatecode);
		et_name = 		(EditText)findViewById(R.id.editName);
		et_password = 	(EditText)findViewById(R.id.editPassword);
		et_confirm = 	(EditText)findViewById(R.id.editComfirm);
		et_email = 		(EditText)findViewById(R.id.editEmail);
		et_phone = 		(EditText)findViewById(R.id.edittext_phonenumber);

		cb_follow = (CheckBox) findViewById(R.id.checkBox1);
		
		tv_privacy = (TextView)findViewById(R.id.textPrivacyLink);
		tv_privacy.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private boolean checkAllInfoFilled() {
		do{			
			if (et_phone.getText().length()!=11){
				Toast.makeText(this, "�绰�����ʽ����ȷ��", Toast.LENGTH_SHORT).show();
				break;
			}
			if (et_code.getText().length()!= 6){
				Toast.makeText(this, "��֤�����", Toast.LENGTH_SHORT).show();
				break;
			}
			if (et_password.getText().length() < 6){
				Toast.makeText(this, "����̫�򵥡�", Toast.LENGTH_SHORT).show();
				break;
			}
			if (!et_confirm.getText().toString().equals(et_password.getText().toString())){
				Toast.makeText(this, "���벻һ�¡�", Toast.LENGTH_SHORT).show();
				break;
			}
			if (et_name.getText().toString().equals("")){
				Toast.makeText(this, "���������֡�", Toast.LENGTH_SHORT).show();
				break;
			}
			if (!cb_follow.isChecked()) {
				Toast.makeText(this, "���Ķ���ʹ���������˽���ߡ���", Toast.LENGTH_SHORT).show();
				break;
			}
			return true;
		}while(false);
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_left) {
			Util.startNewActivity(this, YJWActivity.class, true);
		}else
		if (v.getId() == R.id.bt_right) {
			if (checkAllInfoFilled()) {
				RegisterBean bean = new RegisterBean();
				bean.setSid(sid);
				bean.setCellphone(et_phone.getText().toString());
				bean.setValidateCode(et_code.getText().toString());				
				bean.setEmail(et_email.getText().toString());
				bean.setPassword(et_password.getText().toString());
				bean.setName(et_name.getText().toString());
				RegisterThread t = new RegisterThread();
				t.setStep(RegisterStep.Register);
				t.setBean(bean);
				t.setHandler(handler);
				t.start();
			}
		}
		if (v.getId() == R.id.btn_validatecode){
			String phone = et_phone.getText().toString();
			if (phone.length()==11){
				RegisterThread t = new RegisterThread();
				t.setStep(RegisterStep.getValidateCode);
				t.setCellphone(phone);
				t.setHandler(handler);
				t.start();
			}
		}
	}
}
