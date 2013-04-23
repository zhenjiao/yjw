package com.app.yjw;

import android.app.Activity;
import android.app.ProgressDialog;

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

	protected void init() {
		this.initViews();
	}
}
