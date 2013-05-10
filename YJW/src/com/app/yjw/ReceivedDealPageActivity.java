package com.app.yjw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yjw.pojo.DealInfo;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.SyncTransThread.SyncType;
import com.app.yjw.widget.DealListView;
import com.app.yjw.widget.DealListView.DealListAdapter;

public class ReceivedDealPageActivity extends BaseTabActivity implements
		OnClickListener, OnItemClickListener ,OnItemLongClickListener{

	Button sync_button;
	Button add_button;
	protected DealListView listview;
	protected DealListAdapter adapter;
	protected List<DealInfo> deal_list = new LinkedList<DealInfo>();

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
				//ArrayList<DealInfo>deallist=new ArrayList<DealInfo>();
				
				/*Iterator it=((ArrayList<DealInfo>) msg.obj).iterator();
				while(it.hasNext()){
					DealInfo di=(DealInfo)it.next();
					if(!di.isoverdue())
					deallist.add(di);
				}
				deal_list.addAll(deallist);*/
				FilterOldDeal((ArrayList<DealInfo>) msg.obj,deal_list);
				//DBProxy.insertDeals(deal_list,"REC");
				//deal_list.addAll((ArrayList<DealInfo>) msg.obj);
				//Log.d("deal num",""+deal_list.size());
				//TODO 插入数据库
				adapter.notifyDataSetChanged();
				break;
			case -1:
				Toast.makeText(ReceivedDealPageActivity.this, "更新错误了。。。",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	private void FilterOldDeal(List<DealInfo> from_list,List<DealInfo> to_list){
		//获取一个礼拜前的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-7 );
		long lastweek = cal.getTime().getTime();
		//根据日期过滤交易
		for(DealInfo deal: from_list){
			//deal_list.remove(deal);
			if(deal.getDate().getTime()>lastweek)
				to_list.add(deal);
			//System.out.println(deal.getDate());
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.received_deal_page);
		initSyncThread(SyncType.Received, handler);
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
		intent.putExtra("from", "Received");
		intent.setClass(this, DealDetailAndReplyPageActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

}
