/*
 * Created By Yiheng Tao
 * DealDetailPageActivity.java
 * 
 */

package com.app.yjw;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.database.DBProxy;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.pojo.MsgInfo.MsgType;
import com.app.yjw.thread.PullMsgThread;
import com.app.yjw.thread.PushMsgThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.YJWMessage;
import com.app.yjw.widget.ChatListView;
import com.app.yjw.widget.ChatListView.ChatAdapter;

/**
 * @class : DealDetailPageActivity
 * @extends : BaseMaskActivity
 * @implements : View.OnClickListener , OnItemClickListener
 */
public class DealDetailAndReplyPageActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private final String TAG = "DealDetailPageActivity";

	DealInfo current_deal;

	Button back_button;
	Button send_button;
	Button forward_button;

	TextView title_textview;
	TextView date_textview;
	TextView refer_fee_textview;
	TextView commision_fee_textview;
	TextView details_textview;
	TextView received_users_hint_textview;

	EditText chat_edittext;
	List<MsgInfo> msgList = new ArrayList<MsgInfo>();
	ChatListView.ChatAdapter adapter;
	ChatListView chatlistview;

	String toUserPhone;

	/*
	 * 
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YJWMessage.GET_SHARED_USER_FAILURE:
				Toast.makeText(DealDetailAndReplyPageActivity.this, "网络错误。请重试",
						Toast.LENGTH_SHORT).show();
				break;
			case YJWMessage.GET_SHARED_USER_NONE:
				Toast.makeText(DealDetailAndReplyPageActivity.this, "暂无",
						Toast.LENGTH_SHORT).show();
				break;
			case YJWMessage.SEND_MESSAGE_SUCCESS:
				Toast.makeText(DealDetailAndReplyPageActivity.this, "发送消息成功",
						Toast.LENGTH_SHORT).show();
				break;
			case YJWMessage.SEND_MESSAGE_FAILURE:
				Toast.makeText(DealDetailAndReplyPageActivity.this, "发送消息失败",
						Toast.LENGTH_SHORT).show();
				break;
			case YJWMessage.GET_MESSAGE_SUCCESS:
				/*
				 * adding the message to Database and the list then nofity the
				 * change
				 */
				@SuppressWarnings("unchecked")
				List<MsgInfo> chatList = (List<MsgInfo>) msg.obj;
				for (MsgInfo chat : chatList) {
					DBProxy.insertChatMessage(current_deal.getId(),
							toUserPhone, chat);
					Log.d(TAG, "get : " + chat.getMsg());
				}
				msgList.addAll(chatList);
				adapter.notifyDataSetChanged();
				break;
			case YJWMessage.GET_MESSAGE_NULL:
			case YJWMessage.GET_MESSAGE_FAILURE:
				break;
			}
		}
	};

	/* the thread for sending the message */
	PushMsgThread chatThread = new PushMsgThread(handler);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deal_detailed_page);
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	protected void initViews() {

		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		forward_button = (Button) findViewById(R.id.bt_right);
		forward_button.setOnClickListener(this);
		send_button = (Button) findViewById(R.id.bt_send);
		send_button.setOnClickListener(this);

		title_textview = (TextView) findViewById(R.id.title);
		date_textview = (TextView) findViewById(R.id.tv_date);
		refer_fee_textview = (TextView) findViewById(R.id.tv_refer_fee);
		commision_fee_textview = (TextView) findViewById(R.id.tv_commission_fee);
		details_textview = (TextView) findViewById(R.id.tv_details);

		chat_edittext = (EditText) findViewById(R.id.et_chat);
		chatlistview = (ChatListView) findViewById(R.id.listview);
		chatlistview.setDividerHeight(0);
	}

	protected void init() {

		initViews();

		Bundle bundle = getIntent().getBundleExtra("key");
		String from = getIntent().getStringExtra("from");
		if (bundle != null)
			current_deal = (DealInfo) bundle.getSerializable("deal");
		if (current_deal == null) {
			Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
			return;
		}

		chatThread.setDeal(current_deal);

		if (from.equals(RecommendedDealPageActivity.class.toString()))
			toUserPhone = getIntent().getStringExtra("phonenumber");
		else
			toUserPhone = current_deal.getCreatorPhone();

		title_textview.setText(current_deal.getTitle());
		date_textview.setText(current_deal.getDate().toLocaleString());
		refer_fee_textview.setText(current_deal.getReferFee());
		commision_fee_textview.setText(current_deal.getCommissionFee());
		details_textview.setText(current_deal.getDetails());

		fillHistoryChat();

		adapter = new ChatAdapter(this, R.layout.chatting_item_from,
				R.layout.chatting_item_to, msgList);
		chatlistview.setAdapter(adapter);

		receivedMessage();
	}

	private void fillHistoryChat() {
		msgList.addAll(DBProxy.getHistoryMessage(current_deal.getId(),
				toUserPhone));
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			intent = new Intent();
			intent.putExtra("from", this.getClass().toString());
			intent.putExtra("deal_id", current_deal.getId());
			intent.setClass(this, ContactsActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_send:
			this.sendMessage();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}

	private void receivedMessage() {
		MainPageActivity.msgThread.setMode(PullMsgThread.Mode.Displaying);
		MainPageActivity.msgThread.setDisplayDealId(current_deal.getId());
		MainPageActivity.msgThread.setDisplayHandler(this.handler);
		MainPageActivity.msgThread.setChatUser(toUserPhone);
	}

	@Override
	protected void onStop() {
		MainPageActivity.msgThread.setMode(PullMsgThread.Mode.Running);
		MainPageActivity.msgThread.setDisplayDealId(null);
		MainPageActivity.msgThread.setDisplayHandler(null);
		MainPageActivity.msgThread.setChatUser(null);
		super.onStop();
	}

	private void sendMessage() {
		MsgInfo chat = new MsgInfo(chat_edittext.getText().toString(),
				MsgType.TO);
		DBProxy.insertChatMessage(current_deal.getId(), toUserPhone, chat);

		/* add to current listview */
		msgList.add(chat);
		adapter.notifyDataSetChanged();

		/* reset the edittext */
		chat_edittext.setText("");

		// hide keyboard
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(this.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		// TODO
		chatThread.setChat(chat);
		chatThread.setToUser(toUserPhone);
		new Thread(chatThread).start();
	}

}
