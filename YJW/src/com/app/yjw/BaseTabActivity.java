package com.app.yjw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;

import com.app.yjw.thread.SyncDealThread;
import com.app.yjw.thread.SyncDealThread.SyncType;

public class BaseTabActivity extends Activity {

	protected ProgressDialog loadingDialog;

	protected SyncDealThread syt;
	protected EditText search_edittext;

	protected void jump(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}

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

	protected void initSyncThread(SyncType type, Handler handler) {
		syt = new SyncDealThread(0, type);
		syt.setHandler(handler);
	}

	protected void sync() {
		new Thread(syt).start();
	}

	protected void hideLoadingDialog() {
		if (loadingDialog.isShowing())
			loadingDialog.dismiss();
	}

}
