/*
 * Created By Yiheng Tao
 * DealReplyPageActivity.java
 * 
 * This main aim for this activity is to chat with the deal creator and the deal receiver
 * then two can discuss things about the current deal
 * currently the message only contains characters
 * later will add the ability to send voice and image
 */

package com.app.yjw;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.database.DBProxy;
import com.app.yjw.pojo.BaseUserInfo;
import com.app.yjw.pojo.DealInfo;
import com.app.yjw.pojo.MsgInfo;
import com.app.yjw.pojo.MsgInfo.MsgType;
import com.app.yjw.thread.PullMsgThread;
import com.app.yjw.thread.PushMsgThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.util.YJWMessage;
import com.app.yjw.widget.ChatListView;
import com.app.yjw.widget.ChatListView.ChatAdapter;

/*
 * class : DealReplyPageActivity
 * extends : BaseMaskActivity
 * implements : View.OnClickListener
 */
public class DealReplyPageActivity extends BaseActivity implements
		OnClickListener {

	private final String TAG = "DealReplyPageActivity";

	/* buttons */
	Button back_button;
	Button send_button;

	/* textview */
	TextView title_textview;

	/* the passed deal from DealDetailedPageActivity */
	DealInfo current_deal;

	/*
	 * chat list view to show the message
	 * 
	 * more information to see :
	 * http://blog.sina.com.cn/s/blog_6ff32fbd0100yzz3.html
	 */
	ChatListView chatlistview;

	/*
	 * current messages should be added to this list for display after every
	 * action about it should notify the adapter
	 */
	List<MsgInfo> msgList = new ArrayList<MsgInfo>();

	/*
	 * customized adapter for chat list view
	 */
	ChatListView.ChatAdapter adapter;

	/* the input box at the bottom of the page */
	EditText chat_edittext;

	/* chat with whom */
	private String to_user_phone;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YJWMessage.SEND_MESSAGE_SUCCESS:
				Toast.makeText(DealReplyPageActivity.this, "发送消息成功",
						Toast.LENGTH_SHORT).show();
				break;
			case YJWMessage.SEND_MESSAGE_FAILURE:
				Toast.makeText(DealReplyPageActivity.this, "发送消息失败",
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
							to_user_phone, chat);
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
		setContentView(R.layout.deal_reply_page);
		init();
		receivedMessage();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	/**
	 * Function Name : init
	 * 
	 * @description : init the views and the adapter . the design is needed to
	 *              change
	 * @throws null
	 * @param null
	 * @author Yiheng Tao
	 * 
	 */
	protected void init() {
		super.init();
		fill_deal();

		// TODO need change, not robust
		if (getIntent().getStringExtra("from").equals("reply")) {
			to_user_phone = current_deal.getCreatorPhone();
		} else {
			BaseUserInfo info = (BaseUserInfo) getIntent()
					.getSerializableExtra("user");
			to_user_phone = info.getPhoneNumber();
		}
		fill_history_chat();
		adapter = new ChatAdapter(this, R.layout.chatting_item_from,
				R.layout.chatting_item_to, msgList);
		chatlistview.setAdapter(adapter);
		chatThread.setDeal(current_deal);

	}

	private void fill_history_chat() {
		msgList.addAll(DBProxy.getHistoryMessage(current_deal.getId(),
				to_user_phone));
	}

	private void fill_deal() {
		current_deal = (DealInfo) getIntent().getSerializableExtra("deal");
		if (current_deal == null) {
			Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
			return;
		}
		title_textview.setText(current_deal.getTitle());
	}

	private void receivedMessage() {
		MainPageActivity.msgThread.setMode(PullMsgThread.Mode.Displaying);
		MainPageActivity.msgThread.setDisplayDealId(current_deal.getId());
		MainPageActivity.msgThread.setDisplayHandler(this.handler);
		MainPageActivity.msgThread.setChatUser(to_user_phone);
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
		DBProxy.insertChatMessage(current_deal.getId(), to_user_phone, chat);

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
		chatThread.setToUser(to_user_phone);
		new Thread(chatThread).start();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_send:
			this.sendMessage();
			break;
		}
	}

	@Override
	protected void initViews() {
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);

		send_button = (Button) findViewById(R.id.bt_send);
		send_button.setOnClickListener(this);

		title_textview = (TextView) findViewById(R.id.title);

		chatlistview = (ChatListView) findViewById(R.id.listview);
		chatlistview.setDividerHeight(0);

		chat_edittext = (EditText) findViewById(R.id.et_chat);
	}

}
