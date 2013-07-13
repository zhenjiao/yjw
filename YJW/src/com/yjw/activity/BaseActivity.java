package com.yjw.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;

import com.yjw.ctrl.YJWControler;

public abstract class BaseActivity extends Activity {

	protected ProgressDialog loadingDialog;
	
	protected void showLoadingDialog() {
		if (loadingDialog == null) {
			// �������ڴ�����
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setTitle("Ӷ����");
			loadingDialog.setMessage("���ڼ��أ����Ժ�...");
			loadingDialog.setCancelable(true);
		} else {
			if (!loadingDialog.isShowing())
				loadingDialog.show();
		}
	}

	protected void hideLoadingDialog() {
		if (loadingDialog.isShowing())
			loadingDialog.dismiss();
	}

	protected abstract void initViews();
	public void refresh(Message msg){};

	protected void init() {
		this.initViews();
		YJWControler.getInstance().setActivity(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		YJWControler.getInstance().setActivity(this);
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		YJWControler.getInstance().setActivity(this);
		super.onRestart();
	}
}
