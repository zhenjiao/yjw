package com.app.yjw;

import java.util.List;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.app.yjw.database.DBStatic;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.thread.PullMsgThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.ThreadController;
import com.app.yjw.util.Utility;
import com.app.yjw.util.YJWMessage;


public class MainPageActivity extends TabActivity implements OnClickListener {

	private TabHost mHost;
	private Intent mReceivedDealPageIntent;
	private Intent mRecommendedDealPageIntent;
	private Intent mReferredDealPageIntent;
	private Intent mSettingsPageIntent;
	private RadioButton[] mRadioButton = new RadioButton[4];

	private Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YJWMessage.GET_MESSAGE_NULL:
				Log.d("msg", "null");
				break;
			case YJWMessage.GET_MESSAGE_SUCCESS:
				@SuppressWarnings("unchecked")
				List<MsgInfo> chatList = (List<MsgInfo>) msg.obj;
				for (MsgInfo chat : chatList) {
					ContentValues cv = new ContentValues();
					cv.put("dealid", chat.getDealId());
					cv.put("type", "from");
					cv.put("msg", chat.getMsg());
					YJWActivity.database.insert(DBStatic.MessageTableName,
							null, cv);
					showNotification(R.drawable.about_ooxx, chat);
				}
				break;
			case YJWMessage.GET_MESSAGE_FAILURE:
				Log.d("msg", "shibai");
				break;
			}
		}
	};

	public static PullMsgThread msgThread = new PullMsgThread();
	public static Thread BackgroundThread = new Thread(msgThread);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_page);
		this.init();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Utility.CheckUpdate(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
		msgThread.setMode(PullMsgThread.Mode.Running);
	}
	
	private void init() {
		// init buttons
		mRadioButton[0] = (RadioButton) findViewById(R.id.main_tab_received);
		mRadioButton[1] = (RadioButton) findViewById(R.id.main_tab_recommend);
		mRadioButton[2] = (RadioButton) findViewById(R.id.main_tab_refer);
		mRadioButton[3] = (RadioButton) findViewById(R.id.main_tab_settings);
		for (int i = 0; i < 4; i++)
			mRadioButton[i].setOnClickListener(this);
		this.mHost = getTabHost();
		// init intent
		mReceivedDealPageIntent = new Intent(this,
				ReceivedDealPageActivity.class);
		mRecommendedDealPageIntent = new Intent(this,
				RecommendedDealPageActivity.class);
		mReferredDealPageIntent = new Intent(this,
				ReferredDealPageActivity.class);
		mSettingsPageIntent = new Intent(this, SettingsPageActivity.class);
		// init tabs
		this.mHost.addTab(buildTabSpec("tab-1",
				R.string.main_received_deal_page, R.drawable.ic_dialog_time,
				this.mReceivedDealPageIntent));
		this.mHost.addTab(buildTabSpec("tab-2",
				R.string.main_recommended_deal_page, R.drawable.ic_dialog_time,
				this.mRecommendedDealPageIntent));
		this.mHost.addTab(buildTabSpec("tab-3",
				R.string.main_referred_deal_page, R.drawable.ic_dialog_time,
				this.mReferredDealPageIntent));
		this.mHost.addTab(buildTabSpec("tab-4", R.string.main_settings_page,
				R.drawable.ic_dialog_time, this.mSettingsPageIntent));

		msgThread.setMode(PullMsgThread.Mode.Running);
		msgThread.setHandler(mainHandler);
		//ThreadController.getInstance().addThread(msgThread);
		ThreadController.getInstance().addThread(BackgroundThread);
		
		if (!BackgroundThread.isAlive())
			BackgroundThread.start();
		
	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			new AlertDialog.Builder(this)
					.setTitle("Ӷ����")
					.setMessage("ȷ���˳���")
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									finish();
									YJWActivity.database.close();
									ThreadController.getInstance().killAll();
									System.exit(0);
								}

							}).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_tab_received:
			setRadioButtonCheck(0);
			break;
		case R.id.main_tab_recommend:
			setRadioButtonCheck(1);
			break;
		case R.id.main_tab_refer:
			setRadioButtonCheck(2);
			break;
		case R.id.main_tab_settings:
			setRadioButtonCheck(3);
			break;
		}
	}

	private void setRadioButtonCheck(int n) {
		for (int i = 0; i < 4; i++)
			mRadioButton[i].setChecked(false);
		mRadioButton[n].setChecked(true);
		mHost.setCurrentTab(n);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private NotificationManager notificationManager;
	private int notification_id = 523121325;

	/*
	 * copy from internet
	 */
	public void showNotification(int icon, MsgInfo chat) {
		// Notification������
		Notification notification = new Notification(icon, "Ӷ����",
				System.currentTimeMillis());
		// ����Ĳ����ֱ�����ʾ�ڶ���֪ͨ����Сͼ�꣬Сͼ���Ե����֣�������ʾ���Զ���ʧ��ϵͳ��ǰʱ�䣨�����������ʲô�ã�
		notification.defaults = Notification.DEFAULT_SOUND;
		// ��������֪ͨ�Ƿ�ͬʱ�����������񶯣�����ΪNotification.DEFAULT_SOUND
		// ��ΪNotification.DEFAULT_VIBRATE;
		// LightΪNotification.DEFAULT_LIGHTS�����ҵ�Milestone�Ϻ���ûʲô��Ӧ
		// ȫ��ΪNotification.DEFAULT_ALL
		// ������񶯻���ȫ����������AndroidManifest.xml������Ȩ��
		Intent intent = new Intent();
		intent.setClass(this, DealReplyPageActivity.class);
		intent.putExtra("from", this.getClass().toString());
		intent.putExtra("deal_id", chat.getDealId());
		PendingIntent pt = PendingIntent.getActivity(this, 0, intent, 0);
		// ���֪ͨ��Ķ���
		notification.setLatestEventInfo(this, "xxx������Ϣ", chat.getMsg(), pt);
		notificationManager.notify(notification_id, notification);
	}
}
