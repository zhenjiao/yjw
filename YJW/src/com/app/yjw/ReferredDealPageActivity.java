package com.app.yjw;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yjw.pojo.DealInfo;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.SyncTransThread.SyncType;
import com.app.yjw.widget.DealListView;
import com.app.yjw.widget.DealListView.DealListAdapter;

public class ReferredDealPageActivity extends BaseTabActivity implements
		OnClickListener, OnItemClickListener {

	Button sync_button;
	Button add_button;
	protected DealListView listview;
	protected DealListAdapter adapter;
	protected List<DealInfo> deal_list = new ArrayList<DealInfo>();

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				long number = ((Long) msg.obj).longValue();
				System.out.println(number);
				break;
			case 1:
				deal_list.clear();
				deal_list.addAll((ArrayList<DealInfo>) msg.obj);
				for(DealInfo deal : deal_list)
					deal.isCreator = true;
				// TODO 插入数据库
				//DBProxy.insertDeals(deal_list,"FWD");
				adapter.notifyDataSetChanged();
				break;
			case -1:
				Toast.makeText(ReferredDealPageActivity.this, "更新错误了。。。",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			sync();
			break;
		case R.id.bt_right:
			jump(AddDealPageActivity.class);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		DealInfo deal = deal_list.get((int) arg3);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("deal", deal);
		intent.putExtra("key", bundle);
		intent.putExtra("from", "Referred");
		intent.setClass(this, DealDetailAndReplyPageActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refered_deal_page);
		initSyncThread(SyncType.Referred, handler);
		System.out.println("referred");
		init();
		sync();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	private void init() {
		sync_button = (Button) findViewById(R.id.bt_left);
		sync_button.setOnClickListener(this);
		add_button = (Button) findViewById(R.id.bt_right);
		add_button.setOnClickListener(this);

		listview = (DealListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		listview.setCacheColorHint(0);
		adapter = new DealListAdapter(this, R.layout.received_deal_item,
				deal_list);
		listview.setAdapter(adapter);
		search_edittext = (EditText) findViewById(R.id.search);
	}

}
