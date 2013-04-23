package com.app.yjw;

import android.app.Activity;
import android.app.ProgressDialog;

public abstract class BaseActivity extends Activity {

	protected ProgressDialog loadingDialog;

	protected void showLoadingDialog() {
		if (loadingDialog == null) {
			// 设置正在处理窗口
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setTitle("佣金王");
			loadingDialog.setMessage("正在加载，请稍后...");
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
