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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.app.yjw.pojo.BaseUserInfo;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.ExpandDealInfo;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.thread.SyncDealThread.SyncType;
import com.app.yjw.widget.DealExpandListView;
import com.app.yjw.widget.DealExpandListView.DealExpandListAdapter;

public class RecommendedDealPageActivity extends BaseTabActivity implements
		OnClickListener, OnChildClickListener {

	Button sync_button;
	Button add_button;
	protected DealExpandListView listview;
	protected DealExpandListAdapter adapter;
	protected List<ExpandDealInfo> deal_list = new ArrayList<ExpandDealInfo>();

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
				ArrayList<ExpandDealInfo> dealInfoList = (ArrayList<ExpandDealInfo>) msg.obj;
				//deal_list.addAll(dealInfoList);
				
				convertDealInfo(dealInfoList,deal_list);
				// TODO 插入数据库
				adapter.notifyDataSetChanged();
				break;
			case -1:
				Toast.makeText(RecommendedDealPageActivity.this, "更新错误了。。。",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	private void convertDealInfo(List<ExpandDealInfo> from_list,List<ExpandDealInfo> to_list){
		for(ExpandDealInfo deal: from_list){
			deal.getDeal().isCreator = true;
			boolean is_created = false;
			for(ExpandDealInfo expdeal: to_list){
				if(expdeal.getDeal().getId() == deal.getDeal().getId()){
					expdeal.addReplyer(new BaseUserInfo(null,deal.getDeal().getCreatorPhone() , deal.getDeal().getCreatorName()));
					is_created = true;
					break;
				}
			}
			if(!is_created){
				deal.addReplyer(new BaseUserInfo(null,deal.getDeal().getCreatorPhone() , deal.getDeal().getCreatorName()));
				to_list.add(deal);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommended_deal_page);
		initSyncThread(SyncType.Recommended, handler);
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

		listview = (DealExpandListView) findViewById(R.id.listview);
		listview.setOnChildClickListener(this);
		adapter = new DealExpandListAdapter(this, R.layout.received_deal_item,
				R.layout.chat_user_item, deal_list);
		listview.setAdapter(adapter);
		listview.setCacheColorHint(0);
		search_edittext = (EditText) findViewById(R.id.search);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return getParent().onKeyDown(keyCode, event);
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
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		System.out.println("BEGIN");
		DealInfo deal = deal_list.get(groupPosition).getDeal();
		Intent intent = new Intent();
		intent.setClass(this, DealDetailAndReplyPageActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("deal", deal);
		intent.putExtra("key", bundle);
		intent.putExtra("from", RecommendedDealPageActivity.class.toString());
		intent.putExtra("phonenumber", deal_list.get(groupPosition).getReplyers()
				.get(childPosition).getPhoneNumber());
		startActivity(intent);
		System.out.println("BEGIN");
		return true;
	}

}
