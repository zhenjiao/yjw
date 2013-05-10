package com.app.yjw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;

import com.app.yjw.thread.SyncTransThread;
import com.app.yjw.thread.SyncTransThread.SyncType;
import com.app.yjw.util.BeanPacker;
import com.yjw.bean.GetInfoBean;

public class BaseTabActivity extends Activity {

	protected ProgressDialog loadingDialog;

	protected SyncTransThread syt;
	protected EditText search_edittext;

	protected void jump(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}

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

	protected void initSyncThread(SyncType type, Handler handler) {
		syt = new SyncTransThread();
		syt.setBean(new BeanPacker(YJWActivity.user).transTo(GetInfoBean.class));
		syt.setHandler(handler);
	}

	protected void sync() {
		syt.start();
	}

	protected void hideLoadingDialog() {
		if (loadingDialog.isShowing())
			loadingDialog.dismiss();
	}

}
